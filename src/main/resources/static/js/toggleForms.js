$(document).ready(function(){
    $('.tab a').on('click', function (e) {
        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        var href = $(this).attr('href');
        $('.forms > div > form').hide();
        $(href).fadeIn(500);
    });
});