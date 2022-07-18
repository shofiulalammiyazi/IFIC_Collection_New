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
});