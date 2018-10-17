<%--
  Created by IntelliJ IDEA.
  User: Rone
  Date: 2018/9/29 0029
  Time: 上午 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ECharts</title>
</head>
<body>
    <button type="button" onclick="downloadPDF()">下载PDF</button>
    <div style="display: none;">
        <form id="form_downloadPDF" action="/rone/downloadPDF" method="post"></form>
    </div>

    <br><hr>

    <div id="first_echarts" style="width: 49%; height: 300px;display: inline-block;"></div>
    <div id="second_echarts" style="width: 49%; height: 300px;display: inline-block;"></div>
    <div id="third_echarts" style="width: 49%; height: 300px;display: inline-block;"></div>
    <div id="fourth_echarts" style="width: 49%; height: 300px;display: inline-block;"></div>

</body>
<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/echarts.js"></script>
<script type="text/javascript">
    function downloadPDF() {
        var firstBase64Data = (echarts.init(document.getElementById("first_echarts"))).getDataURL();
        var secondBase64Data = (echarts.init(document.getElementById("second_echarts"))).getDataURL();
        var thirdBase64Data = (echarts.init(document.getElementById("third_echarts"))).getDataURL();
        var fourthBase64Data = (echarts.init(document.getElementById("fourth_echarts"))).getDataURL();

        $("#form_downloadPDF").append("<input type='text' name='firstBase64Data' value='" + firstBase64Data + "'>");
        $("#form_downloadPDF").append("<input type='text' name='secondBase64Data' value='" + secondBase64Data + "'>");
        $("#form_downloadPDF").append("<input type='text' name='thirdBase64Data' value='" + thirdBase64Data + "'>");
        $("#form_downloadPDF").append("<input type='text' name='fourthBase64Data' value='" + fourthBase64Data + "'>");
        $("#form_downloadPDF").submit();

        // console.log(firstBase64Data);
        // console.log(secondBase64Data);
        // console.log(thirdBase64Data);
        // console.log(fourthBase64Data);
    }

    $(function () {
        var firstOption = {
            title : {
                text: '某站点用户访问来源',
                subtext: '纯属虚构',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:335, name:'直接访问'},
                        {value:310, name:'邮件营销'},
                        {value:234, name:'联盟广告'},
                        {value:135, name:'视频广告'},
                        {value:1548, name:'搜索引擎'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        var secondOption = {
            title : {
                text: '客户分布',
                subtext: '2018',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['杭州市','浙江省','江苏省','上海市','北京市'],
                formatter: function(name) {
                    var target;
                    var data = secondOption.series[0].data;
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].name == name) {
                            target = data[i].value;
                        }
                    }
                    return '' + name + ' ' + target;
                }
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : ['50%', '70%'],
                    center: ['50%', '60%'],
                    data:[
                        {value:100, name:'杭州市'},
                        {value:310, name:'浙江省'},
                        {value:101, name:'江苏省'},
                        {value:170, name:'上海市'},
                        {value:50, name:'北京市'}
                    ],
                    label: {
                        normal: {
                            show: true,
                            position: 'inner',
                            formatter: '{b} {d}%'
                        }
                    }
                }
            ]
        };

        var thirdOption = {
            title : {
                text: '存款交易量',
                subtext: '2018'
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'蒸发量',
                    type:'bar',
                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    }
                }
            ]
        };

        var fourthOption= {
            title: {
                text: '各支行存款额',
                subtext: '2018'
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['义务总部','杭州中心','舟山支行','湖州支行','嘉兴支行','金华支行']
            },
            series: [
                {
                    name: '存款额',
                    type: 'bar',
                    data: [2900, 2100, 890, 1089, 1190, 200],
                    label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    }
                }
            ]
        };

        var firstECharts = echarts.init(document.getElementById("first_echarts"));
        firstECharts.setOption(firstOption);

        var secondECharts = echarts.init(document.getElementById("second_echarts"));
        secondECharts.setOption(secondOption);

        var thirdECharts = echarts.init(document.getElementById("third_echarts"));
        thirdECharts.setOption(thirdOption);

        var fourthECharts = echarts.init(document.getElementById("fourth_echarts"));
        fourthECharts.setOption(fourthOption);

    });
</script>
</html>
