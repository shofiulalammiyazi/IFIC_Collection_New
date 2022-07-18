/*
l//$(document).ready(function() {

	//global value
	var fractionFlag=0;
	var comma=0;
	var __delimiter = ",";
	var __standard = $('#appliedLoanHidden').text();
  	var __fraction=".";

  	$("#quantity").keypress(function(e){
		if(key_filter(e)){
			return $("#quantity").val(bd_style_decimal_marker($("#quantity").val()));
		}else
			return false;
		
	});
  	
  	

	function bd_style_decimal_marker(str){
		
		var arr = [];
		if(__standard=='' || __standard==null){
			__standard = 'bd';
		}
		if(__delimiter==='.'){
	    	__fraction=',';
	     	__standard = "iso";
    	}
		arr = str.split(__fraction);
		if(arr.length>1){
			return bd_decimal_mark(arr[0])+__fraction+arr[1]+'0';
		}
		else{
			return bd_decimal_mark(str);
		}
	}

	function bd_decimal_mark(value){
		value = delimiter_eliminator(value);
		var _temp = [];
		for (var i = 0;i<value.length;i++) {
	    	_temp.push(value[i]);
	    }
		if(__standard!='bd' && __standard!='ind'){
			var i = _temp.length-1,j=3;
			for (; 0 <= i ; i--) {
	            if(j<=_temp.length){
	                _temp.splice((_temp.length)-j,0,__delimiter);
	                j=j+4;
	            }
			}
			return _temp.join('');
		}
		else{
	    	var i = _temp.length-1,j=3,k=0;
			for (; 0 <= i ; i--) {
				if(k===3){
					k=0;
					j++;
				}
	            if(j<=_temp.length-1){
	                _temp.splice(_temp.length-j,0,__delimiter);
	                j=j+3;
					k++;
	            }
			}
			return _temp.join('');
		}
	}

	function delimiter_eliminator(raw_string){
		var _arr = raw_string.split(__delimiter);
		return _arr.join('');
	}

	function key_filter(e){
  	isFraction(e.target.value);
		if(fractionFlag===0){
	    	if(e.which ===46 || (e.which >47 && e.which <=57) || e.which ===44 || ($.inArray(e.which,[8, 9, 27, 13, 110, 190,0]) !== -1)){
	    		if(e.which===46){
	      			fractionFlag++;
              }
	    		return true;
	      	}else
	      		return false;
	    }else if(comma===0){
	    	if( (e.which >47 && e.which <=57) || e.which ===44 || ($.inArray(e.which,[8, 9, 27, 13, 110, 190,0]) !== -1)){
			    if(e.which===44)
			    	comma++;
			    return true;
			}else
			    return false;
	    }else{
	    	if( (e.which >47 && e.which <=57) || ($.inArray(e.which,[8, 9, 27, 13, 110, 190,0]) !== -1)){
	      		return true;
	      	}else
	      		return false;
	    }
	}

	function isFraction(value){
		for (var i = 0; i < value.length; i++) {
			if (value[i]!==__fraction) {
      			fractionFlag=0;
			}
		}
	}

	function configure(delimiter,standard){
		__delimiter = delimiter;
		__standard = standard
	}
//});*/
