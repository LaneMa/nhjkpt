function loadDataByAjax(url,id,title,xtitle,ytitle,filename){
	var chart;
	$.ajax({
		type : "POST",
		url : url,
		success : function(jsondata) {
			data = eval(jsondata);
			var arr=new Array();
			if(data[0]){
				if(data[0]){
					for(var i=0,l=data[0].data.length;i<l;i++){
						arr.push(data[0].data[i].name);
						
					}
				}
				chart = new Highcharts.Chart({
					chart : {
						renderTo : id,
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false
					},
					title : {
						text : title
					},
					xAxis : {
						title:{
							text:xtitle
						},
						categories : arr
					},
					yAxis:{
						title:{
							text:ytitle
						}
					},
					tooltip : {
						pointFormat : '{series.name}: <b>{point.y}</b>',
						percentageDecimals : 1

					},
					exporting:{  
						enabled:false,
		                filename:filename,  
		                url:'${ctxPath}/logController.do?export',
						buttons:{  
							exportButton:{  
								enabled:false  
							},  
							printButton:{  
								enabled:false  
		
							}
						}
		            }, 
		            credits:{  
                        enabled: true,  
                        href: "#",  
                        text: ''  
                    },
					plotOptions : {
						pie : {
							allowPointSelect : true,
							cursor : 'pointer',
							showInLegend : true,
							dataLabels : {
								enabled : true,
								color : '#000000',
								connectorColor : '#000000',
								formatter : function() {
									return '<b>' + this.point.name + '</b>: ' + this.point.y;
								}
							}
						}
					},
					series : data
				});
			}else{
				tip("没有数据!");
			}
		}
	});
}