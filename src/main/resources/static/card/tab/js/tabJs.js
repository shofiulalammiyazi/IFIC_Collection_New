$(document).ready(function() {
	

	
	
    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
    });
});



$(document).ready(function() {



    var pgurl = window.location.pathname;
    $("ul.sidebar-menu ul li a").each(function () {
        if ($(this).attr("href") == pgurl || $(this).attr("href") == '') {
            $(this).parent("li").addClass("active");
            $(this).closest("li.treeview").addClass("active");
            // $(this).closest("ul.treeview-menu").hide();
        }
    });


	/*
	$("#pending-task-tree").removeClass("active");
	var link = window.location;
	var menu_link = $('.treeview>.treeview-menu>li');
	$(menu_link).each(function(index, element){
		var $activeLink = $(element).find('a');
		if( $activeLink.attr('href') == link.pathname ) {
			$activeLink.parents('li').addClass('active');
		}
		$("#pending-task-tree").removeClass("active");
	

	});
	*/
	
	
	/*
	 $("ul.sidebar-menu>li.treeview>ul.treeview-menu>li").click(function(e) {
		 e.preventDefault();
		        $("ul.sidebar-menu>li.menu-open>ul.treeview-menu>li").addClass("active");
		        $(this).siblings('a.active').removeClasss("active");
		        $(this).addClass("active");
		         var index= $(this).index();
		        $("ul.sidebar-menu>li.treeview>ul.treeview-menu>li").removeClasss("active");
		        $("ul.sidebar-menu>li.treeview>ul.treeview-menu>li>a").eq(index).addClass("active");
	 });
	 */
	/*$("#inboxblock").click(function(){
		//alert('hi');
		var link = window.location;
		var menu_link = $('.treeview>.treeview-menu>li');
			if( $activeLink.attr('href') == link.pathname ) {
				$activeLink.parents('li').addClass('active');
				//$("ul.block").css('display','block');
			}
		//$("ul.block").css('display','block');
//		style="display: block;"
	});
	*/
	
	
});