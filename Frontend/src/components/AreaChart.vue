<template>
  <div>
    <b-sidebar id="chartbar" right shadow>
      <div class="container-fluid" style="margin-top: 30px">
        <div class="chart-title">
          <span class="dongname-highlight">{{ fetchedDongName }}</span
          >의 생활인구 분석
        </div>
        <b-collapse id="age-chart" style="">
          <canvas id="ageChart"></canvas>
        </b-collapse>
        <b-button v-b-toggle.age-chart class="chart-toggle-button">
          <span class="open">닫기</span>
          <span class="closed" @click="showAgeChart"> 조회 </span>
        </b-button>
        <hr />
        <div class="chart-title">
          <span class="dongname-highlight">{{ fetchedDongName }}</span
          >의 아파트 순위(높은 가격 순)
        </div>
        <b-collapse id="price-top-chart">
          <canvas id="priceTopChart"></canvas>
        </b-collapse>
        <b-button v-b-toggle.price-top-chart class="chart-toggle-button">
          <span class="open">닫기</span>
          <span class="closed" @click="showPriceTopChart"> 조회 </span>
        </b-button>
        <hr />
        <div class="chart-title">
          <span class="dongname-highlight">{{ fetchedDongName }}</span
          >의 평균 집값 추이
        </div>
        <b-collapse id="price-avg-chart">
          <canvas id="priceAvgChart"></canvas>
        </b-collapse>
        <b-button v-b-toggle.price-avg-chart class="chart-toggle-button">
          <span class="open">닫기</span>
          <span class="closed" @click="showPriceAvgChart"> 조회 </span>
        </b-button>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import { Chart } from 'chart.js';
import http from '@/util/http-common';
import { mapGetters } from 'vuex';
var color = Chart.helpers.color;
var randomcolor = require('randomcolor');
// Chart.register(...registerables);
export default {
  name: 'AreaChart',
  data() {
    return {
      ageChartConfig: '',
      ageChart: '',
      priceTopChartConfig: '',
      priceTopChart: '',
      priceAvgChartConfig: '',
      priceAvgChart: '',
    };
  },
  mounted() {
    this.createAgeChart();
    this.createPriceTopChart();
    this.createPriceAvgChart();
  },
  computed: {
    ...mapGetters(['fetchedDongName']),
  },
  methods: {
    createAgeChart() {
      let ca = document.getElementById('ageChart');
      this.agechartconfig = {
        type: 'bar',
        data: {
          labels: [
            '0-9세',
            '10-14세',
            '15-19세',
            '20-24세',
            '25-29세',
            '30세-34세',
            '35세-39세',
            '40세-44세',
            '45세-50세',
            '50세-54세',
            '55세-60세',
            '60세-64세',
            '65세-70세',
            '70세-74세',
          ],
          datasets: [
            {
              label: '남성',
              data: [],
              borderColor: 'rgba(54, 162, 235, 0.69)',
            },
            {
              label: '여성',
              data: [],
              borderColor: 'rgba(255, 99, 132, 0.69)',
            },
          ],
        },
        options: {
          elements: {
            rectangle: {
              borderWidth: 10,
            },
          },
          responsive: true,
          legend: {
            position: 'right',
          },
        },
      };
      this.ageChart = new Chart(ca, this.agechartconfig);
      // this.agechart.update();
    },
    createPriceTopChart() {
      let cpt = document.getElementById('priceTopChart');
      this.priceTopChartConfig = {
        type: 'horizontalBar',
        data: {
          labels: [],
          datasets: [
            {
              label: '단위(1천만)',
              data: [],
              backgroundColor: [],
            },
          ],
          options: {
            elements: {
              rectangle: {
                borderWidth: 10,
              },
            },
            responsive: true,
            legend: {
              position: 'right',
            },
          },
        },
      };
      this.priceTopChart = new Chart(cpt, this.priceTopChartConfig);
    },
    createPriceAvgChart() {
      let cpa = document.getElementById('priceAvgChart');
      this.priceAvgChartConfig = {
        type: 'line',
        data: {
          labels: [
            '1월',
            '2월',
            '3월',
            '4월',
            '5월',
            '6월',
            '7월',
            '8월',
            '9월',
            '10월',
            '11월',
            '12월',
          ],
          datasets: [
            {
              data: [],
              borderColor: 'rgba(18, 89, 112, 0)',
              backgroundColor: 'rgba(18, 89, 112, 0.69)',
              pointRadius: 1.5,
              pointHoverRadius: 10,
            },
          ],
        },
        options: {
          legend: {
            display: false,
          },
          responsive: true,
          lineTension: 80,
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                  padding: 0,
                },
              },
            ],
          },
          events: ['click'],
        },
        hover: {
          mode: 'nearest',
        },
      };
      this.priceAvgChart = new Chart(cpa, this.priceAvgChartConfig);
    },
    showAgeChart() {
      if (this.ageChart.data.datasets[0].data.length > 0) {
        this.ageChart.data.datasets[0].data = [];
        this.ageChart.data.datasets[1].data = [];
      }

      let hjcode = '';
      http.get('/analysis/gugun/' + this.fetchedDongName).then((response) => {
        // console.log(data);
        let guguncode = response.data;
        http
          .get(
            'http://openapi.seoul.go.kr:8088/775a766a4e7a70643535524c63586c/json/bigCmpBjdongMgmInfo/1/500/' +
              guguncode
          )
          .then(({ data }) => {
            let rows = data.bigCmpBjdongMgmInfo.row;
            for (let ele of rows) {
              if (ele.BJDONG_NM == this.fetchedDongName && ele.HJDONG_CD != '000') {
                hjcode = String(guguncode).concat(String(ele.HJDONG_CD));
                break;
              }
            }

            console.log(hjcode);
            // 인구 데이터 받으러
            http
              .get(
                'http://openapi.seoul.go.kr:8088/775a766a4e7a70643535524c63586c/json/SPOP_LOCAL_RESD_DONG/1/24/20210520/15/' +
                  hjcode
              )
              .then(({ data }) => {
                let rows = data.SPOP_LOCAL_RESD_DONG.row;
                // console.log(rows[0]);
                for (let key in rows[0]) {
                  if (key.includes('FEMALE')) {
                    this.ageChart.data.datasets[1].data.push(rows[0][key]);
                  }
                  if (key.includes('MALE')) {
                    this.ageChart.data.datasets[0].data.push(rows[0][key]);
                  }
                }

                this.ageChart.update();
              });
          });
      });
    },
    showPriceTopChart() {
      if (this.priceTopChart.data.datasets[0].data.length > 0) {
        console.log(this.priceTopChart.data.datasets[0].data.length);
        this.priceTopChart.data.datasets[0].data = [];
        this.priceTopChart.data.labels = [];
      }

      http.get('/analysis/top/' + this.fetchedDongName).then(({ data }) => {
        // this.priceTopChart.data.datasets[0].data = [];
        // this.priceTopChart.data.labels = [];
        data.forEach((ele) => {
          this.priceTopChart.data.labels.push(ele.aptname);
          // console.log(parseInt(ele.maxprice.trim()));
          this.priceTopChart.data.datasets[0].data.push(parseInt(ele.maxprice.trim()));
          this.priceTopChart.data.datasets[0].backgroundColor.push(
            color(randomcolor()).alpha(0.5).rgbString()
          );
        });
        this.priceTopChart.update();
      });
    },
    showPriceAvgChart() {
      if (this.priceAvgChart.data.datasets[0].data.length > 0) {
        console.log(this.priceAvgChart.data.datasets[0].data.length);
        this.priceAvgChart.data.datasets[0].data = [];
      }
      http.get('/analysis/avg/' + this.fetchedDongName).then(({ data }) => {
        console.log(data);
        for (let row of data) {
          this.priceAvgChart.data.datasets[0].data.push(row.price);
        }
        this.priceAvgChart.update();
      });
    },
  },
};
</script>

<style>
#chartbar {
  width: 750px;
}
.collapsed > .open,
:not(.collapsed) > .closed {
  display: none;
}

#chartbar.b-sidebar.b-sidebar-right > .b-sidebar-header .close {
  color: black !important;
}
.dongname-highlight {
  color: #125970;
  font-size: 18px;
}
.chart-title {
  padding: 20px 11px;
}
.chart-toggle-button {
  margin: 15px 11px;
}
</style>
