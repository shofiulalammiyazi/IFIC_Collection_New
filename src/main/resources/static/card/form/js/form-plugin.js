(function($) {
	$.fn.addModal = function (){

		var modal = '';
		if($('#form-submition-modal').length===0){
			createFormMessageModal();
		}

		this.append(modal);
		
		function createFormMessageModal(){ 
			modal =	"<div class='modal fade' id='form-submition-modal'>"+
					        "<div class='modal-dialog'>"+
					            "<div class='modal-content'>"+
					              	"<div class='modal-header'>"+
					                	"<button type='button' class='close' data-dismiss='modal' aria-label='Close'>"+
					                  		"<span aria-hidden='true'>&times;</span></button>"+
					                		"<h4 class='modal-title'>Message</h4>"+
					              	"</div>"+
					              	"<div class='modal-body'>"+
					                	
					              	"</div>"+
					              	"<div class='modal-footer'>"+
					                	"<button type='button' class='btn btn-cncl' data-dismiss='modal'>Close</button>"+
					              	"</div>"+
					            "</div>"+
					        "</div>"+
					    "</div>";
		}
		return this;
	}

	$.fn.setTitle = function (title){
		$('#form-submition-modal').find('.modal-title').empty().append(title);
		return this;
	}

	$.fn.setBody = function(body){
		$('#form-submition-modal').find('.modal-body').empty().append(body);
		return this;
	}

	$.fn.showModal = function(){
		$('#form-submition-modal').modal('show');
		return this;
	}

}( jQuery ));