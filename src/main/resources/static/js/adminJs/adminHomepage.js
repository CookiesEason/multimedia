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
        let tag = null;
        let num = null;
        axios.get('/api/data/smallTag/topFive').then(function (res) {
            console.log(res.data)
            num = res.data[0];
            tag = res.data[1];
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
                        data : tag,
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
                        data:num
                    }
                ]
            };
            echartsB.setOption(option);
            $(window).resize(function(){
                echartsA.resize();
                echartsB.resize();
            })
        })

        
})