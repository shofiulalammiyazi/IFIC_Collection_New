
$(document).ready(function(){
	
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": true,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
    

	$("#generalProposal").hide();
	$("#reviewType").change(function() {
		var a = $(this).val();
		console.log(a);
		if (a == "enhancement" || a=="ren reduction") {
			//$(".proposaltable1").show();
			//$(".proposaltable2").hide();
			$(".proposaltable1").hide();
			$(".proposaltable2").show();
		} else {
			/*$(".proposaltable1").hide();
			$(".proposaltable2").show();*/
			$(".proposaltable1").show();
			$(".proposaltable2").hide();
		}
	});


	$('.corporate_clone_row').click(function(event) {

		$(".datepicker").datepicker("destroy");
    	$(".datepicker").removeClass("hasDatepicker").removeAttr('id');

		var trlast = $(this).closest('table').find('tbody').find('tr:last');
		var trNew = trlast.clone();
		var lId = trNew.find('.gen-loanId-basic').val();
		var cId = trNew.find('.customer-cls').val();
		trNew.find('input').not('.un-change').val('');
		trNew.find('.gen-loanId-basic').val(lId);
		trNew.find('.customer-cls').val(cId);
		trlast.after(trNew);

		$(".datepicker").datepicker({
			autoclose : true
		});
		
	});

});

$(document).on('click', '.delete-row', function () { // <-- changes
     
		if (confirm('Want to delete?')) {
			var rowCount = $(this).closest('tbody').find('tr').length;
			if(rowCount<2){
				$(this).closest('tr').find('input').val("");
			}else{
				$(this).closest('tr').remove();
			}
	    }

     return false;
 });

/*$(document).on('click', '.corporate_clone_row', function(event) {
	var trlast = $(this).closest('table').find('tbody').find('tr:last');
	var trNew = trlast.clone();
	var lId = trNew.find('.gen-loanId-basic').val();
	trNew.find('input').val('');
	trNew.find('.gen-loanId-basic').val(lId);
	trlast.after(trNew);
});*/

