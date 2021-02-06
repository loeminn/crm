<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
    <script type="text/javascript">
        var searchName, searchOwner, searchStartDate, searchEndDate, editId;
        $(function () {
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });
            $("#addBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        $("#create-owner").empty();
                        $.each(data, function (i, n) {
                            let html = $("<option></option>");
                            html.val(n.id).text(n.name);
                            $("#create-owner").append(html).val("${user.id}");
                        })
                    }
                })
                $("#createActivityModal").modal("show");
            })
            $("#saveBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/save.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            $("#activityAddForm")[0].reset();
                            $("#createActivityModal").modal("hide");
                            pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            alert("添加市场活动失败");
                        }
                    }
                })
            })
            $("#searchBtn").click(function () {
                searchName = $.trim($("#search-name").val());
                searchOwner = $.trim($("#search-owner").val());
                searchStartDate = $.trim($("#search-startDate").val());
                searchEndDate = $.trim($("#search-endDate").val());
                pageList(1, 5);
            })
            $("#qx").change(function () {
                $("input[name=xz]").prop("checked", this.checked);
            })
            $("#activityBody").on("click", "input[name=xz]", function () {
                $("#qx").prop("checked", $("input[name=xz]:checked").length === $("input[name=xz]").length)
            })
            $("#deleteBtn").click(function () {
                let $xz = $("input[name=xz]:checked");
                if ($xz.length === 0) {
                    alert("请选择需要删除的记录");
                } else {
                    if (confirm("确定删除所选记录吗？")) {
                        let param = "";
                        $.each($xz, function (i, n) {
                            param += "id=" + n.value + "&";
                        })
                        param = param.substring(0, param.length - 1);
                        $.ajax({
                            url: "workbench/activity/delete.do",
                            data: param,
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                if (data.success) {
                                    pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                                } else {
                                    alert("删除市场活动失败")
                                }
                            }
                        })
                    }
                }
            })
            $("#editBtn").click(function () {
                let $xz = $("input[name=xz]:checked");
                if ($xz.length === 0) {
                    alert("请选择需要修改的记录")
                } else if ($xz.length > 1) {
                    alert("只能同时选择一条记录进行修改");
                } else {
                    let id = $xz.val();
                    $.ajax({
                        url: "workbench/activity/getUserListAndActivity.do",
                        data: {"id": id},
                        type: "get",
                        dataType: "json",
                        success: function (data) {
                            $("#edit-owner").empty();
                            $.each(data.uList, function (i, n) {
                                let $html = $("<option></option>");
                                $html.val(n.id).text(n.name);
                                $("#edit-owner").append($html).val(data.a.owner);
                            })
                            editId = data.a.id;
                            $("#edit-name").val(data.a.name);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);
                        }
                    })
                    $("#editActivityModal").modal("show");
                }
            })
            $("#updateBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/update.do",
                    data: {
                        "id": editId,
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            $("#editActivityModal").modal("hide");
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            alert("修改市场活动失败");
                        }
                    }
                })
            })
            pageList(1, 5);
        });

        function pageList(pageNo, pageSize) {
            $("#qx").prop("checked", false);
            $.ajax({
                url: "workbench/activity/pageList.do",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": searchName,
                    "owner": searchOwner,
                    "startDate": searchStartDate,
                    "endDate": searchEndDate
                },
                type: "get",
                dataType: "json",
                success: function (data) {
                    let html = "";
                    $.each(data.dataList, function (i, n) {
                        html += "<tr class='active'>" +
                            "<td><input type='checkbox' name='xz' value='" + n.id + "'/></td>" +
                            "<td><a style='text-decoration: none; cursor: pointer;'onclick = 'window.location.href=\"workbench/activity/detail.do?id=" + n.id + "\";'>" + n.name + "</a></td>" +
                            "<td>" + n.owner + "</td>" +
                            "<td>" + n.startDate + "</td>" +
                            "<td>" + n.endDate + "</td>" +
                            "</tr>";
                    })
                    $("#activityBody").html(html);
                    let totalPages = Math.ceil(data.total / pageSize);
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数
                        visiblePageLinks: 3, // 显示几个卡片
                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }
            })
        }
    </script>
</head>
<body>

    <!-- 创建市场活动的模态窗口 -->
    <div class="modal fade" id="createActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" id="activityAddForm" role="form">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-owner">
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">名称<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="create-startDate" readonly>
                            </div>
                            <label class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="create-endDate" readonly>
                            </div>
                        </div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="create-description"></textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改市场活动的模态窗口 -->
    <div class="modal fade" id="editActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-owner">
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">名称<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-startDate">
                            </div>
                            <label class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-endDate">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-description"></textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
                </div>
            </div>
        </div>
    </div>


    <div>
        <div style="position: relative; left: 10px; top: -10px;">
            <div class="page-header">
                <h3>市场活动列表</h3>
            </div>
        </div>
    </div>
    <div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
        <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

            <div class="btn-toolbar" role="toolbar" style="height: 80px;">
                <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">名称</div>
                            <input id="search-name" class="form-control" type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">所有者</div>
                            <input id="search-owner" class="form-control" type="text">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">开始日期</div>
                            <input class="form-control" type="text" id="search-startDate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">结束日期</div>
                            <input class="form-control" type="text" id="search-endDate">
                        </div>
                    </div>

                    <button type="button" id="searchBtn" class="btn btn-default">查询</button>

                </form>
            </div>
            <div class="btn-toolbar" role="toolbar"
                 style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
                <div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-primary" id="addBtn"><span
                            class="glyphicon glyphicon-plus"></span> 创建
                    </button>
                    <button type="button" class="btn btn-default" id="editBtn">
                        <span class="glyphicon glyphicon-pencil"></span> 修改
                    </button>
                    <button type="button" class="btn btn-danger" id="deleteBtn"><span
                            class="glyphicon glyphicon-minus"></span> 删除
                    </button>
                </div>

            </div>
            <div style="position: relative;top: 10px;">
                <table class="table table-hover">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td><input type="checkbox" id="qx"/></td>
                        <td>名称</td>
                        <td>所有者</td>
                        <td>开始日期</td>
                        <td>结束日期</td>
                    </tr>
                    </thead>
                    <tbody id="activityBody">
                    </tbody>
                </table>
            </div>
            <div style="height: 50px; position: relative;top: 30px;">
                <div id="activityPage"></div>
            </div>
        </div>
    </div>
</body>
</html>