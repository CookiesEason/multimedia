$(function(){
	/* 图表插件 */
	/* 访问量 */
	var echartsA = echarts.init(document.getElementById('amount-of-access'));
        option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '3%',
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                data: ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00']
            }],
            yAxis: [{
                type: 'value'
            }],
            textStyle: {
                color: '#838FA1'
            },
            series: [{
                name: '今日访问量',
                type: 'line',
                stack: '总量',
                areaStyle: { normal: {} },
                data: [120, 132, 101, 134, 90,20,60],
                itemStyle: {
                    normal: {
                        color: '#1cabdb',
                        borderColor: '#1cabdb',
                        borderWidth: '2',
                        borderType: 'solid',
                        opacity: '1'
                    },
                    emphasis: {

                    }
                }
            }]
        };
        echartsA.setOption(option);
    /* 标签 */
    var echartsB = echarts.init(document.getElementById('hot-tags-chart'));
        echartsB.title = '五大热门标签';

		option = {
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '0%',
		        right: '3%',
		        bottom: '4%',
		        top:'3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : [ '美食', '文化', '地域', '饮料', '故事'],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'作品量',
		            type:'bar',
		            barWidth: '60%',
		            data:[ 200, 334, 390, 330, 220]
		        }
		    ]
		};
		echartsB.setOption(option);
        $(window).resize(function(){
        	echartsA.resize();
        	echartsB.resize();
        })
        
})