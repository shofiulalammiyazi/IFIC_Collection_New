 $(document).ready(function(){
			$("#add_new_row").click(function(){
				var rowNum = parseInt($('.particular-assign >tbody >tr.main-particular').last().find(".ins-row-particular-val").val()) + 1;
				previousRownum = rowNum - 1;
				var clone = $('.particular-assign >tbody >tr.main-particular').first().clone(true).prop("id","particularRowId_"+rowNum).removeAttr("style");
				clone.find(".row-particular-val").text(rowNum);
				clone.find(".ins-row-particular-val").val(rowNum);
				clone.addClass("serial-"+rowNum).removeClass("serial-0");
				clone.addClass("condition-div-clss-serial-"+rowNum).removeClass("condition-div-clss-serial-0");

				clone.find('#del-row-0').attr({id: "del-row-"+rowNum});
				clone.find('#particularId-0').attr({id: "particularId-"+rowNum});
				clone.find('#cardParticularId-0').attr({id: "cardParticularId-"+rowNum});
				clone.find('#maxId-0').attr({id: "maxId-"+rowNum});
				clone.find('#add-new-condition-0').attr({id:"add-new-condition-"+rowNum});
				clone.find('#condition-segment-0').attr({id:"condition-segment-"+rowNum});
				clone.find('#conditional-div-0').attr({id:"conditional-div-"+rowNum});
				clone.find('#conditional-div-'+rowNum).addClass("delete-condition-"+rowNum+"-1").removeClass("delete-condition-0-0");
				clone.find('#condition-0-0').attr({id:"condition-"+rowNum+"-1"});
				clone.find('#point-0-0').attr({id:"point-"+rowNum+"-1"});
				clone.find('#delCondition-0-0').attr({id:"delCondition-"+rowNum+"-1"});
				clone.find('#conditionPointId-0-0').attr({id:"conditionPointId-"+rowNum+"-1"});
				clone.find('.max-point-hidden').addClass("maxPoint-cls");
				clone.find('.con-main').removeClass("con-cls-0").addClass("con-cls-"+rowNum);
				clone.find('.point-main').removeClass("point-cls-0").addClass("point-cls-"+rowNum);
				$('#table-particular').append(clone);
			    
			});
		});
		
		function createCondition(conditionBtnId){
			var addButtonName = conditionBtnId.id;
			
			var splitAddButtonNameWithComma= addButtonName.split("add-new-condition-");
		    var ButtonNo = splitAddButtonNameWithComma.join("");
			var conditionRowNumber = parseInt($('.condition-div-clss-serial-'+ButtonNo).last().find(".ins-row-conditional-val").val()) + 1;
			var preConditionRowNumber = conditionRowNumber -1; 
			var maxPointAddCondition = parseInt($("#maxId-"+ButtonNo).val());
				
			/*var sum = 0;
			$('.point-cls-'+ButtonNo).each(function(){
				    sum += parseInt(this.value);
			});
			console.log("sum: "+ sum);
			if(sum>maxPointAddCondition ){
				$("#alertMessagePS").text('');
				$("#particularError").val('');
				var message = "<ul style='list-style-type:disc'><li>Max value exceed</li></ul>";
				$("#alertMessagePS").append(message);
				$("#particularError").modal("toggle");

			}
			else{*/
				var conditionClone = $('#conditional-div-0').first().clone(true).prop("id","conditional-div-"+conditionRowNumber);
				conditionClone.addClass("condition-div-clss-serial-"+ButtonNo).removeClass("condition-div-clss-serial-0");
				conditionClone.addClass("delete-condition-"+ButtonNo+"-"+conditionRowNumber).removeClass("delete-condition-0-0");
			    conditionClone.find('#condition-0-0').attr({id: "condition-"+ButtonNo+"-"+conditionRowNumber}).removeClass("con-clss-0");
			    conditionClone.find('#point-0-0').attr({id: "point-"+ButtonNo+"-"+conditionRowNumber});
			    conditionClone.find('#delCondition-0-0').attr({id: "delCondition-"+ButtonNo+"-"+conditionRowNumber});
			    conditionClone.find('#conditionPointId-0-0').attr({id: "conditionPointId-"+ButtonNo+"-"+conditionRowNumber});
			   	conditionClone.find('.con-main').removeClass("con-cls-0").addClass("con-cls-"+ButtonNo);
			    conditionClone.find('.point-main').removeClass("point-cls-0").addClass("point-cls-"+ButtonNo);
			  
			    conditionClone.find(".conditionLabel").text(conditionRowNumber);
			    conditionClone.find(".ins-row-conditional-val").val(conditionRowNumber);
			    
				$('#condition-segment-'+ButtonNo).append(conditionClone);
			//}
				
		};
		
			$(document).ready(function(){
				$(".delete-row").click(function(){
					if (confirm('Want to delete?')) {
						var rowId=$(this).attr("value");
						if(!$(this).attr("value")){
							$(this).parent().parent().remove();	
						}else{
						
				          $.ajax({
				            type: "GET",
				            context: this,
				            url: "/creditscoringcriteria/delete/" + rowId,
				            success: function (data) {
				            	$(this).parent().parent().remove();	
				            },
				            error: function (jqXHR, exception) {
				                showUnivarsalErrorMsg(jqXHR, exception);
				            }
				        });  
						}
				    }
				});
				
			});
			
				$(document).ready(function(){
						$(".delete-condition").click(function(){
							if (confirm('Want to delete?')) {
							var conDlbtn= $(this).attr("id").split("delCondition").join("");
							var rowId=$(this).attr("value");
							if(!$(this).attr("value")){
								$(this).parent('div.delete-condition'+conDlbtn).remove();
							}else{
							$.ajax({
						            type: "GET",
						            context: this,
						            url: "/condition/delete/" + rowId,
						            success: function (data) {
						            		
						            	$(this).parent('div.delete-condition'+conDlbtn).remove();
						            },
						            error: function (jqXHR, exception) {
						                showUnivarsalErrorMsg(jqXHR, exception);
						            }
						        });  
							}
				    }
				});
				
			});

			function saveParticularSelection() {
				var maxPointSum = 0;
				$('.maxPoint-cls').each(function(){
					maxPointSum += parseInt(this.value);
					    
				});
				
				if(maxPointSum>100){
					$("#alertMessagePS").text('');
					$("#particularError").val('');
					var message = "<ul style='list-style-type:disc'><li>Maximum Point does not  exceed 100</li></ul>";
					$("#alertMessagePS").append(message);
					$("#particularError").modal("toggle");
				}else {

					var cardTypeId = $("#cardTypeId").val();
					var appTypeId = $("#appTypeId").val();

					var cardTypeName=$("#cardTypeId option:selected").text();
					var appTypeName=$("#appTypeId option:selected").text();

				var particularSelectionId = $("#particularSelection").val();
				var rowNumber= 1;
				var particularIdMaxPoint = [];
			    $('#table-particular > tbody  > tr').not(":first").each(function() {
			    	var conditionCounter=1;
			        var elementValues =[];
			        var cardParticularId= $("#cardParticularId-"+rowNumber).val();
			        var particularElementId = $("#particularId-"+rowNumber).val();
			        var maxElementId = $("#maxId-"+rowNumber).val();
			        rowNumber=rowNumber+1;
			        var conditionTemp;
		        	var pointTemp;
		        	var conditionPointIdTemp;
		        	var associativeArray=[];
			        $(this).find(".conditional-td input[type!='hidden'] ").each(function(){
			       			if(Number(conditionCounter)%3==1){
			       				conditionPointIdTemp=this.value;
			       			}
			        		if(Number(conditionCounter)%3==2){
			        			conditionTemp=this.value;
			        		}
			        		if(Number(conditionCounter)%3==0){
			        			pointTemp=this.value;
			        			
			        			associativeArray.push({
			                        "conditionPointId":conditionPointIdTemp,
			        				"point": pointTemp,
			                        "conditon": conditionTemp
			                    }); 
			        			
			        		}
						conditionCounter=conditionCounter+1;
					
			         });
			        particularIdMaxPoint.push({
			        	
			       			"cardParticularId":cardParticularId,
			        		"particulars": particularElementId, 
			        		"maxPoint": maxElementId,
						    "conditionPointEntityList":associativeArray
			        		/*"point": pointTemp,
			                "conditon": conditionTemp*/
			        }
			        		);
			      
			        //console.log(particularIdMaxPoint);
			       });
			    var cardParticularDTO={
			    	"appTypeId":appTypeId,
			    	"cardTypeId":cardTypeId,
			    	"cardTypeName":cardTypeName,
			    	"appTypeName":appTypeName,
			    	"cardParticularEntityList":particularIdMaxPoint
			    };
				var obj={"cardParticularDTO":cardParticularDTO};
			    var dto=JSON.stringify(cardParticularDTO);

			    var token = $("meta[name='_csrf']").attr("content");
			    var header = $("meta[name='_csrf_header']").attr("content");
				$.ajax({
					url : '/card/facility-wise-particular/save',
					type : 'POST',
					contentType : 'application/json; charset=utf-8',
					dataType : 'JSON',
					data: dto,
					beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
					},
					success : function(data) {

                        $("#alertMessagePS").text('');
						$("#particularError").val('');
						var message = "<ul style='list-style-type:disc'><li>Successfully saved</li></ul>";
						$("#alertMessagePS").append(message);
						 $("#particularError").modal("toggle");
						
						$("#cardTypeId").val('');
						$("#appTypeId").val('');
						$("#cardTypeId").val(data.cardTypeId);
					    $("#appTypeId").val(data.appTypeId);

                        $("#cardTypeName").val('');
                        $("#appTypeName").val('');
                        $("#cardTypeName").val(data.cardTypeName);
                        $("#appTypeName").val(data.appTypeName);
					    
						 for(var i=0;i<data.cardParticularEntityList.length;i++){
							//var cardParticularCounter=parseInt(i)+1;

							$("#cardParticularId-"+(i+1)).val('');
							$("#particularId-"+(i+1)).val('');
							$("#maxId-"+(i+1)).val('');
							$("#del-row-"+(i+1)).val('');
							$("#del-row-"+(i+1)).val(data.cardParticularEntityList[i].cardParticularId);
							$("#cardParticularId-"+(i+1)).val(data.cardParticularEntityList[i].cardParticularId);
							$("#particularId-"+(i+1)).val(data.cardParticularEntityList[i].particulars);
							$("#maxId-"+(i+1)).val(data.cardParticularEntityList[i].maxPoint);
							 for(var j=0;j<data.cardParticularEntityList[i].conditionPointEntityList.length;j++){

								 $("#conditionPointId-"+(i+1)+"-"+(j+1)).val('');
								 $("#condition-"+(i+1)+"-"+(j+1)).val('');
								 $("#point-"+(i+1)+"-"+(j+1)).val('');
								 $("#delCondition-"+(i+1)+"-"+(j+1)).val('');
								 $("#conditionPointId-"+(i+1)+"-"+(j+1)).val(data.cardParticularEntityList[i].conditionPointEntityList[j].conditionPointId);
								 $("#condition-"+(i+1)+"-"+(j+1)).val(data.cardParticularEntityList[i].conditionPointEntityList[j].conditon);
								 $("#point-"+(i+1)+"-"+(j+1)).val(data.cardParticularEntityList[i].conditionPointEntityList[j].point);
								 $("#delCondition-"+(i+1)+"-"+(j+1)).val(data.cardParticularEntityList[i].conditionPointEntityList[j].conditionPointId);
															 }
							
						}
                        window.location.href = "/card/facility-wise-particular/list";
                    },
					error : function(ERR) {
						
						alert("Error:"+ERR);
					}
				});
				}// else end
			};
			
		
			$('#table-particular').on('keyup', "input[type!='hidden'].point-tgr", function(ev){
				var parentTd=$(this).closest('td').attr('id');
				var allPointClssName = $(this).attr('class');
				var pointClassName = allPointClssName.split("form-control point-main point-tgr");
				var onlyPointclassName= pointClassName.join(""); 
				var classNameWithdot="."+onlyPointclassName.trim();
				var sequenceRowNo= onlyPointclassName.split("point-cls-");
				var sequenceRowNoWithoutComma=sequenceRowNo.join("");
				var maxPoint= $("#maxId-"+sequenceRowNoWithoutComma.trim()).val();
				var sum = 0;
				$(classNameWithdot).each(function(){
					//sum += parseInt(this.value);
				});
				//if(sum>maxPoint){
				if(parseInt(this.value)>maxPoint){
					$("#alertMessagePS").text('');
					$("#particularError").val('');
					var message = "<ul style='list-style-type:disc'><li>Particular point  doesnot exceed max Point</li></ul>";
					$("#alertMessagePS").append(message);
					$("#particularError").modal("toggle");
				
				}
				
			}); 
			function reset(){
				$('#table-particular > tbody  > tr').not(":first").each(function(){
					 $(this).find("input[type!='hidden'] ").each(function(){
						 $(this).val('');
					 });
					 $(this).find("option:selected").each(function(){
						 $(this).text('Select Particular');
					 });
				});
				
			}
			;