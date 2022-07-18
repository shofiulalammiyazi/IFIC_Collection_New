$(function() {
	
	// crg-edit
	$(".edit-crg-part").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var score = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;

		$('#crg-part-id').find("#partId").val(id);
		$('#crg-part-id').find("#name").val(name);
		$('#crg-part-id').find("#score").val(score);
		$('#crg-part-id').find("#description").val(desc);
	});
	
	// facility-edit
	$(".edit-facility").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var code = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;

		$('#fac-id').find("#facilityId").val(id);
		$('#fac-id').find("#name").val(name);
		$('#fac-id').find("#code").val(code);
		$('#fac-id').find("#description").val(desc);
	});

	// cib-particuller

	$(".edit-cib-particular").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var code = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;

		$('#cib-particullar-id').find("#partId").val(id);
		$('#cib-particullar-id').find("#name").val(name);
		$('#cib-particullar-id').find("#code").val(code);
		$('#cib-particullar-id').find("#description").val(desc);
	});
	
	// sm-particuller

	$(".edit-sm-particular").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var code = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;

		$('#sm-particullar-id').find("#smParId").val(id);
		$('#sm-particullar-id').find("#smPartName").val(name);
		$('#sm-particullar-id').find("#smPartCode").val(code);
		$('#sm-particullar-id').find("#smPartDescription").val(desc);
	});

	// FinancialRecordParticular Edit

	$(".finrecordparticular").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var code = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;

		$('#finrecord').find("#partId").val(id);
		$('#finrecord').find("#name").val(name);
		$('#finrecord').find("#code").val(code);
		$('#finrecord').find("#description").val(desc);
	});
	// BusinessNature Edit

	$(".businessnaturecls").click(function(event) {
		event.preventDefault();
        var id = $(this).get(0).href.split('/').pop();
        var rowIndex = $(this).closest("tr").find(".td");
        var name = rowIndex[0].innerHTML;
        var code = rowIndex[1].innerHTML;
        var desc = rowIndex[2].innerHTML;
        $('#businessnatureid').find("#nobId").val(id);
        $('#businessnatureid').find("#name").val(name);
        $('#businessnatureid').find("#code").val(code);
        $('#businessnatureid').find("#description").val(desc);
    });

	
	//utility- facility
	
	$(".edit-ut").click(function(event) {
		event.preventDefault();
		var id = $(this).get(0).href.split('/').pop();
		var rowIndex = $(this).closest("tr").find(".td");
		var name = rowIndex[0].innerHTML;
		var code = rowIndex[1].innerHTML;
		var desc = rowIndex[2].innerHTML;
		$('#ut-fac-id').find("#utilityId").val(id);
		$('#ut-fac-id').find("#name").val(name);
		$('#ut-fac-id').find("#code").val(code);
		$('#ut-fac-id').find("#description").val(desc);
	});
	

});