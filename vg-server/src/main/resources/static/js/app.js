//地图容器
var chart = echarts.init(document.getElementById('box4'));

var online = [
    {name: '南京', value: [118.78, 32.04, 100]},
    {name: '无锡', value: [120.29, 31.59, 120]},
    {name: '上海', value: [121.48, 31.22, 78]},
    {name: '韵达成都回包线改造', value: [104.06, 30.67, 129]},
    {name: '佛山', value: [113.134026, 23.035095, 88]},
    {name: '中山', value: [113.42206, 22.545178, 63]},
    {name: '武汉', value: [114.3162, 30.581084, 117]},
    {name: '东莞', value: [113.763434, 23.043024, 55]},
    {name: '揭阳', value: [116.379501, 23.547999, 88]},
    {name: '海门', value: [121.15, 31.89, 9]},
    {name: '鄂尔多斯', value: [109.781327, 39.608266, 12]},
    {name: '招远', value: [120.38, 37.35, 12]},
    {name: '舟山', value: [122.207216, 29.985295, 25]},
    {name: '齐齐哈尔', value: [123.97, 47.33, 37]},
    {name: '盐城', value: [120.13, 33.38, 12]},
    {name: '赤峰', value: [118.87, 42.28, 89]},
    {name: '青岛', value: [120.33, 36.07, 22]},
    {name: '乳山', value: [121.52, 36.89, 11]},
    {name: '金昌', value: [102.188043, 38.520089]},
    {name: '泉州', value: [118.58, 24.93, 34]},
    {name: '莱西', value: [120.53, 36.86, 35]},
    {name: '日照', value: [119.46, 35.42, 55]},
    {name: '胶南', value: [119.97, 35.88, 70]},
    {name: '南通', value: [121.05, 32.08, 35]},
    {name: '拉萨', value: [91.11, 29.97, 22]},
    {name: '云浮', value: [112.02, 22.93, 11]},
    {name: '梅州', value: [116.1, 24.55, 12]}
];


var alarming = [
    {name: '银川', value: [106.206479, 38.502621, 112]},
    {name: '余姚', value: [121.579006, 29.885259, 126]},
    {name: '漯河', value: [114.05, 33.58, 123]},
    {name: '深圳', value: [114.025974, 22.546054, 137]}
];

var offline = [
    {name: '西安', value: [108.953098, 34.2778, 112]},
    {name: '南昌', value: [115.893528, 28.689578, 133]},
    {name: '潍坊', value: [119.142634, 36.716115, 128]}
];

var date = new Date();
var mytime = date.toLocaleTimeString();     //获取当前时间

//绘制全国地图
$.getJSON('/map/china.json', function (data) {
    var mapdata = [];
    for (var i = 0; i < data.features.length; i++) {
        mapdata.push({
            name: data.features[i].properties.name
        })
    }
    //注册地图
    echarts.registerMap('china', data);
    //绘制地图
    renderMap('china', mapdata);
});


//初始化绘制全国地图配置
var option = {
    backgroundColor: '',
    title: {
        text: '',
        subtext: '地图',
        left: 'center',
        textStyle: {
            color: '#fff',
            fontSize: 16,
            fontWeight: 'normal',
            fontFamily: "Microsoft YaHei"
        },
        subtextStyle: {
            color: '#ccc',
            fontSize: 13,
            fontWeight: 'normal',
            fontFamily: "Microsoft YaHei"
        }
    },
    //自定义提示框的内容，固定位置
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
        }
    },
    legend: {
        orient: 'vertical',
        y: 'bottom',
        x: 'right',
        data: ['信息'],
        textStyle: {
            color: '#fff'
        }
    },
    //初始化散点图的坐标系，就是底图
    geo: {
        map: 'china',
        label: {
            //如果将此处的label注释掉，地图中将不会显示市级名称
            emphasis: {
                show: false
            }
        },
        itemStyle: {
            normal: {
                //初始化地图背景颜色
                areaColor: '#323c48',
                borderColor: '#fff'
            },
            emphasis: {
                areaColor: '#2a333d'
            }
        }
    },
    animationDuration: 1000,
    animationEasing: 'cubicOut',
    animationDurationUpdate: 1000

};

function renderMap(map, data) {
    if (map == 'china') {
        option.title.subtext = "";
    } else {
        option.title.subtext = map;
    }

    option.series = [
        {
            name: map,
            type: 'map',
            mapType: map,
            roam: false,
            data: data,
            nameMap: {
                'china': '中国'
            },
            label: {
                normal: {
                    show: false,
                    textStyle: {
                        color: '#f4e925',
                        fontSize: 13
                    }
                },
                emphasis: {
                    show: false,
                    textStyle: {
                        color: '#f4e925',
                        fontSize: 13
                    }
                }
            },
            grid: {
                x: 'center',
                y: 'top'
            },
            itemStyle: {
                normal: {
                    areaColor: '#151d4c',
                    borderColor: '#FFFFFF'
                },
                emphasis: {
                    areaColor: '#242e4c',
                    color: '#333'
                }
            }
        },
        {
            name: '在线设备',
            type: 'scatter',
            coordinateSystem: 'geo',
            encode: {
                value: 2
            },
            // data: online.sort(function (a, b) {
            //     return b.value - a.value;
            // }).slice(0, online.length),
            data: online,
            encode: {
                value: 2
            },
            symbolSize: function (val) {
                return val[2] / 10;
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    var res = '<div style="min-width:150px;line-height: 25px;"><b>韵达快递</b><div class="info"><p>' + params.name + '</p></div>';
                    res += "<div><p></p><div style='color:#FD482C;font-size:14px;font-weight: bold;'>安装调试</div></div></div>";
                    return res;
                }
            },
            showEffectOn: 'hover',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            itemStyle: {
                normal: {
                    color: '#008000',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            }
        }, {
            name: '报警设备',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data: alarming,
            encode: {
                value: 2
            },
            symbolSize: 15,
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            }
        },
        {
            name: '离线设备',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: offline,
            encode: {
                value: 2
            },
            symbolSize: 8,
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            itemStyle: {
                normal: {
                    color: '#C0C0C0',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            }
        }
    ];

    chart.setOption(option);
    // chart.on('click', function (params) {
    //     console.log(params);
    //     if (params.componentSubType === 'scatter') {
    //         var areaName = params.name;
    //         $('#box').css('display', 'block');
    //         $('#box-title').html(areaName);
    //     }
    // })
}

function load() {
    $.ajax({
        type: "POST",
        url: "/items",
        data: {
            beginTime: $('#beginTime').val(),
            endTime: $('#endTime').val()
        },
        cache: false,
        async: false,
        success: function (result) {
            var res = [];
            console.log(result);
            for (var i = 0; i < result.length; i++) {
                //获得每个城市的统计数量
                var count = result[i].value;
                //获得经纬度
                var geo = [result[i].longitude, result[i].latitude];
                res.push({
                    name: result[i].name,
                    //两个字符串拼接
                    value: geo.concat(count)
                });
            }
            chart.setOption({
                series: [{
                    name: '项目信息',
                    data: res
                }
                ]
            });
        }
    })
}

function reLoad() {
    load();
}
