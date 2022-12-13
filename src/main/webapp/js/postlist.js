URL = document.URL
resourceposition = URL.indexOf("PostClass");
resourceposition = resourceposition + 10
resourcepositionend = URL.indexOf("?");
postclass = URL.substring(resourceposition, resourcepositionend)
sendurl = "/getPostList/" + postclass
pagepos = URL.indexOf("page=");
page = URL.substring(pagepos + 5)
totalcount = 1;
    axios({
        method: "post",
        url: "/getpostcountServlet?postclass=" + postclass
    }).then(function (resp) {
        totalcount = resp.data.totalcount
        $('.pagination').jqPaginator({
            totalCounts: Number(totalcount),
            pageSize: 10,
            visiblePages: 10,
            first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            currentPage: Number(page),
            onPageChange: function (num, type) {
                if (type == "change") {
                    window.location.href = "/PostClass/" + postclass + "?page=" + num
                }
            }
        })
    })