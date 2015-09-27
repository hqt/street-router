/**
 * Created by dinhquangtrung on 7/3/15.
 */
moment.locale('vi');

function markAsRead(id) {
    $.ajax({
        url: "/notif?action=markAsRead&id=" + id,
        method: 'get',
        dataType: 'json'
    }).done(function (result) {
        if (result) {
            $('.notif-item[data-id=' + id + ']').attr('data-is-read', 'true');
            refreshState();
        }
    });
}
function markAsUnread(id) {
    $.ajax({
        url: "/notif?action=markAsUnread&id=" + id,
        method: 'get',
        dataType: 'json'
    }).done(function (result) {
        if (result) {
            $('.notif-item[data-id=' + id + ']').attr('data-is-read', 'false');
            refreshState();
        }
    });
}

function refreshState() {
    $('.notif-item').each(function () {
        var $item = $(this);
        if ($item.attr('data-is-read') == "true") {
            $item.find('.mark-as-unread').removeClass('dont-show');
            $(this).find('.mark-as-read').css('visibility', 'hidden');
        } else {
            $item.find('.mark-as-unread').addClass('dont-show');
            $(this).find('.mark-as-read').css('visibility', 'visible');

        }
    });
}

function checkNotification() {
    $.ajax({
        url: "/ajax?action=unreadCount",
        method: 'get',
        dataType: 'json'
    }).done(function (count) {
        if (count > 0) {
            $('#unread-notifs').html(count).fadeIn();
        }
    });
}

$(function () {
    checkNotification();
    setInterval(checkNotification, 3000);


    $('#notif-icon').click(function () {
        var $notif = $('#notif-list');
        var isVisible = !$notif.is(':visible');

        if (isVisible) {
            $.ajax({
                url: "/ajax?action=listUnread",
                method: 'get',
                dataType: 'json'
            }).done(function (list) {
                var html = '';
                for (var i = 0; i < list.length; i++) {
                    var notif = list[i];

                    html += '<li>' +
                        '<a class="' + (notif.isRead == 1 ? "is-read" : "") + '" href="/notif?action=markAsRead&id=' + notif.id + '&redirect=true">' +
                        '<div> <i class="fa fa-bell fa-fw"></i> ' +
                        notif.content +
                        '<span class="notification-time pull-right text-muted small">' +
                        moment(notif.createdDate).calendar() +
                        '</span>' +
                        '</div>' +
                        '</a>' +
                        '</li>';
                }

                if (list.length == 0) {
                    html = '<li class="text-center">Không có thông báo mới nào</li>'
                }

                html += '<li class="divider"></li>' +
                    '<li>' +
                    '<a class="text-center" href="/' + $('body').data('role') + '/dashboard">' +
                    '<strong>Xem tất cả thông báo</strong> ' +
                    '<i class="fa fa-angle-right"></i>' +
                    '</a>' +
                    '</li>';

                $notif.html(html);
            });
        }
    });

    $('.mark-as-unread').addClass('dont-show');
    $('.mark-as-read').css('visibility', 'hidden');

    refreshState();

});


function showUnread() {
    $('.notif-item').hide();
    $('[data-is-read=false]').show();
    updateNotifs(false);
}

function showRead() {
    $('.notif-item').hide();
    $('[data-is-read=true]').show();
    updateNotifs(true);
}

// Notification filter
$(function () {
    $('#view-unread-notifs').click(function () {
        showUnread();
    });
    $('#view-read-notifs').click(function () {
        showRead();
    });
    showUnread();
});

function updateNotifs(isRead) {
    if (!isRead) {
        $('#view-unread-notifs').addClass('text-bold');
        $('#view-read-notifs').removeClass('text-bold');
    } else {
        $('#view-unread-notifs').removeClass('text-bold');
        $('#view-read-notifs').addClass('text-bold');
    }
    $('#view-unread-notifs span').html('(' + $('[data-is-read=false]').length + ')');
    $('#view-read-notifs span').html('(' + $('[data-is-read=true]').length + ')');
}