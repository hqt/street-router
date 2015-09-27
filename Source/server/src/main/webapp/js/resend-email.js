/**
 * Created by dinhquangtrung on 6/29/15.
 */


$(function () {
    var $btnResendPassword = $('#btnResendPassword');
    $btnResendPassword.click(function () {

        if ($btnResendPassword.hasClass('sending')) {
            return;
        }

        function restoreBtn() {
            setTimeout(function () {
                $btnResendPassword.removeAttr('disabled');
                $btnResendPassword.removeClass('sending');
                $btnResendPassword.html('<i class="fa fa-refresh"></i> Gửi lại email');
            }, 2000);
        }

        var sendFailed = function () {
            $btnResendPassword.html('<i class="fa fa-times"></i> Gửi không thành công!');
        };

        var sendSuccess = function () {
            $btnResendPassword.html('<i class="fa fa-check"></i> Đã gửi thành công!');
        };

        $btnResendPassword.attr('disabled', 'disabled');
        $btnResendPassword.html('Đang gửi...');
        $btnResendPassword.addClass('sending');

        $.ajax({
            url: '/ajax',
            method: 'post',
            data: {
                action: 'resendPassword',
                customerCode: $btnResendPassword.data('customerCode')
            },
            dataType: 'json'
        }).done(function (msg) {
            if (msg) {
                sendSuccess();
            } else {
                sendFailed();
            }
        }).fail(sendFailed).always(restoreBtn);
    });
})