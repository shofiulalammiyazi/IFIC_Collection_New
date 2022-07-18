var followUpDetails = new Vue({
    el: "#followup",
    data: {
        followUpList: [],
        accountList: []
    },
    methods: {
        selectFollowUp: function (index) {
            this.accountList = this.followUpList[index].accountNumbers;
            setTimeout(function () {
                addFollowUpAccountNumbersTableFilter();
            }, 100);
            $('#modal-follow-up-details').modal('show');

        }
    }
})

function addFollowUpFilter() {
    $('.columnFilterTable').DataTable({
        initComplete: function () {
            this.api().columns().every(function (index) {
                if (index == 1) return;
                var column = this;
                // let columnName = this.context[0].aoColumns[index].sTitle;
                // var select = $('<select class="dropdown-transparent"><option value="">'+columnName+'</option></select>')
                var select = $('<select class="dropdown-transparent pull-right"><option value=""></option></select>')
                    .appendTo($(column.header()))
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
                        column.search(val ? '^' + val + '$' : '', true, false).draw();
                    });
                column.data().unique().sort().each(function (d, j) {
                    select.append("<option value=" + d + ">" + d + "</option>");
                });
            });
        },
        destroy: true,
        // retrieve: true,
        paging: false,
        ordering: false,
        searching: true,
        "bInfo": false
    });
}
function addFollowUpAccountNumbersTableFilter() {
    $('#followUpAccountNumbersTable').DataTable({
        retrieve: true,
        paging: false,
        ordering: false,
        searching: true,
        "bInfo": false
    });
}