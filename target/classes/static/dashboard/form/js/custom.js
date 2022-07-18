$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose : true	
	});
	
	$('table-paginate').DataTable();

	$('.currency').keyup(function(event) {
		event.preventDefault();
	});
	
	$('.check-box-check').change(function(event) {
		if($(this).is(":checked")){
			$(this).val(true);
		}else{
			$(this).val(false);
		}
	});
	
	
	$('#corp-fin-projectTemp-submit').click(function(event){
  		event.preventDefault();
		
  		var response = submitCurrentForm($(this));
		var scope = findViewContainer($(this));
		//console.log(response);
		response.done(function(data) {
			$('#form-submition-modal').find('.modal-body').first().empty().append('Successful');
			//console.log(data);
			$('#pro-projectName-view').text(data.projectName);
			$('#pro-product-view').text(data.product);
			$('#pro-projectLocation-view').text(data.projectLocation);
			$('#pro-landArea-view').text(data.landArea);
			$('#pro-descriptionBuilding-view').text(data.descriptionBuilding);
			$('#pro-owner-view').text(data.owner);
			$('#pro-machinary-view').text(data.machinary);
			$('#pro-projectCost-view').text(data.projectCost);
			$('#pro-utilities-view').text(data.utilities);
			$('#pro-etp-view').text(data.etp);
			$('#pro-exportBuyer-view').text(data.exportBuyer);
			$('#pro-brand-view').text(data.brand);
			$('#pro-noOfEmployee-view').text(data.noOfEmployee);
			$('#pro-commercialProduction-view').text(new Date(data.commercialProduction).toLocaleDateString());
			$('#pro-rawMaterials-view').text(data.rawMaterials);
			$('#pro-finishedGoods-view').text(data.finishedGoods);
			$('#pro-dailyProduction-view').text(data.dailyProduction);
			$('#pro-yearlyProduction-view').text(data.yearlyProduction);
			$('#pro-productionCapacity-view').text(data.productionCapacity);
			$('#pro-dailyActualProduction-view').text(data.dailyActualProduction);
			$('#pro-sellingPricePerUnit-view').text(data.sellingPricePerUnit);
			$('#pro-costperUnit-view').text(data.costperUnit);
			$('#pro-fireFightingArrangement-view').text(data.fireFightingArrangement);
			
			hideForm(scope);
			showViewPanel(scope);

		});
	 
	});

  	$('#corp-fin-projectFinRatio-submit').click(function(event) {
  		event.preventDefault();
  		var response = submitCurrentForm($(this));
		var scope = findViewContainer($(this));

		response.done(function(data) {
			$('#form-submition-modal').find('.modal-body').first().empty().append('Successful');
			$('#npvFirstYear-view').text(data.npvFirstYear);
			$('#npvSecYear-view').text(data.npvSecYear);
			$('#npvThirdYear-view').text(data.npvThirdYear);
			$('#debtRatioFirstYear-view').text(data.debtRatioFirstYear);
			$('#debtRatioSecYear-view').text(data.debtRatioSecYear);
			$('#debtRatioThirdYear-view').text(data.debtRatioThirdYear);
			$('#debServiceFirstYear-view').text(data.debServiceFirstYear);
			$('#debServiceSecYear-view').text(data.debServiceSecYear);
			$('#debServiceThirdYear-view').text(data.debServiceThirdYear);
			$('#irrFirstYear-view').text(data.irrFirstYear);
			$('#irrSecYear-view').text(data.irrSecYear);
			$('#irrThirdYear-view').text(data.irrThirdYear);
			$('#breakEvenFirstYear-view').text(data.breakEvenFirstYear);
			$('#breakEvenSecYear-view').text(data.breakEvenSecYear);
			$('#breakEvenThirdYear-view').text(data.breakEvenThirdYear);	
			hideForm(scope);
			showViewPanel(scope);

		});
  	});
  	
  	
  	$('#corp-fin-asset-submit').click(function(event) {
		event.preventDefault();
		var response = submitCurrentForm($(this));
		var scope = findViewContainer($(this));

		response.done(function(data) {
			$('#form-submition-modal').find('.modal-body').first().empty().append('Successful');
			var body = scope.find('tbody');
			if (data.length != 0) {
				$.each(data, function(index, val) {
					var sl = index + 1;
					var string = '<tr>' 
							+ '<td><span class="hide">' + val.id+ '</span>' + val.particulars + '</td>'
							+ '<td>' + val.amount+'</td></tr>';
					body.append(string);
				});
			}

			hideForm(scope);
			showViewPanel(scope);

		});
	});
  	
  	
  	$('#corp-fin-liability-submit').click(function(event) {
		event.preventDefault();
		var response = submitCurrentForm($(this));
		var scope = findViewContainer($(this));

		response.done(function(data) {
			$('#form-submition-modal').find('.modal-body').first().empty().append('Successful');
			var body = scope.find('tbody');
			if (data.length != 0) {
				$.each(data, function(index, val) {
					var sl = index + 1;
					var string = '<tr>' 
							+ '<td><span class="hide">' + val.id+ '</span>' + val.particulars + '</td>'
							+ '<td>' + val.amount+'</td></tr>';
					body.append(string);
				});
			}

			hideForm(scope);
			showViewPanel(scope);

		});
	});
  	
  	
  	
  	
	
});



$('.multi-line-broadcom-div ul li').click(function() {
    $('.multi-line-broadcom-div ul li').removeClass('active');
});


