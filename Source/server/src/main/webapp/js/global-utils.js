$(function () {
    $('form').on('submit', function () {
        $(this).find('[type=submit]').attr('disabled','disabled');
    });

    var $modifyDetail = $('.more-detail');
    var $reason = $('#modify-reason');
    if ($modifyDetail.length > 0 && $reason.val()) {
        $modifyDetail.html("Chi tiết: " + $reason.val());
    }
});