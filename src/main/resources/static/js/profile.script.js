// @ts-ignore
$('#logout').on('click', function () {
    // @ts-ignore
    document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    // @ts-ignore
    window.location.href = '/';
});
