//提交的方法名称
var method = "";
var listParam = "";
var saveParam = "";
$(function() {
    //加载表格数据
    $('#grid').datagrid({
        url: name + '/getAll' + listParam,
        columns: columns,
        singleSelect: true,
        pagination: true
    });

    //点击查询按钮
    $('#btnSearch').bind('click', function () {
        //把表单数据转换成json对象
        var formData = $('#searchForm').serializeJSON();
        $('#grid').datagrid('load', formData);
    });

    var h = 300;
    var w = 400;
    if (typeof (height) != "undefined") {
        h = height;
    }
    if (typeof (width) != "undefined") {
        w = width;
    }
    //初始化编辑窗口
    $('#editDlg').dialog({
        title: '编辑',//窗口标题
        width: w,//窗口宽度
        height: h,//窗口高度
        closed: true,//窗口是是否为关闭状态, true：表示关闭
        modal: true//模式窗口
    });

    //点击保存按钮
    $('#btnSave').bind('click', function () {
        //做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
        var isValid = $('#editForm').form('validate');
        if (isValid == false) {
            return;
        }
        var formData = $('#editForm').serializeJSON();
        $.ajax({
            url: name + '/' + method + saveParam,
            data: formData,
            dataType: 'json',
            type: 'post',
            success: function (rtn) {
                $.messager.alert("提示", rtn.message, 'info', function () {
                    //成功的话，我们要关闭窗口
                    $('#editDlg').dialog('close');
                    //刷新表格数据
                    $('#grid').datagrid('reload');
                });
            }
        });
    });
});


    /**
     * 删除
     */
    function del(uuid) {
        $.messager.confirm("确认", "确认要删除吗？", function (yes) {
            if (yes) {
                $.ajax({
                    url: name + '/delete?id=' + uuid,
                    dataType: 'json',
                    type: 'post',
                    success: function (rtn) {
                        $.messager.alert("提示", rtn.message, 'info', function () {
                            //刷新表格数据
                            $('#grid').datagrid('reload');
                        });
                    }
                });
            }
        });
    }

    /**
     * 查看文档
     */
    function edit(uuid) {
        //是否拥有权限查看
        $.ajax({
            url: name + '/check?id=' + uuid,
            dataType: 'json',
            type: 'post',
            success: function (rtn) {
                if (rtn.success){
                    window.location.href="word?filepath=" + rtn.filepath;
                }else{
                    $.messager.confirm('提示','您没有权限查看该文档，请申请提升用户等级',function(r){
                    });
                }
            }
        });

    }