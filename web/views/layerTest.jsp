<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/22 0022
  Time: 下午 2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LayerTest</title>
</head>
<body>

<button onclick="button0()">带确认按钮的提示框</button><br/><br/>
<button onclick="button1()">简单的提示</button><br/><br/>
<button onclick="button2()">确认框</button><br/><br/>
<button onclick="button3()">简单提示框类型的确认框</button><br/><br/>
<button onclick="button4()">输入框</button><br/><br/>
<button onclick="button5()">带图标的提示</button><br/><br/>
<button onclick="button6()">加载动画</button><br/><br/>
<button onclick="button7()">带文字的加载动画</button><br/><br/>
<button onclick="button8()" id="btn_tips">tips</button><br/><br/>
<button onclick="button9()">捕获页</button><br/><br/>
<a href="https://layer.layui.com/" target="_blank">更多请移至layer的官网</a>

</body>
<div id="div_show" style="display: none">
    <h1>layer的Demo</h1>
    <ul>
        <li>1.提示框</li>
        <li>2.确认框</li>
        <li>3.弹出层</li>
        <li>4.待探索</li>
    </ul>
</div>
<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/layer/layer.js"></script>
<script type="text/javascript">
    //带确认按钮的提示框
    function button0() {
        //参数1：提示框内的内容
        //参数2：设置一些属性
        layer.alert('带确认按钮的提示框',  {icon: 6, title: '自定义的标题'});
    }
    //简单的提示
    function button1() {
        layer.msg('小提示，待会会自己关闭',function() {
            //添加了这个function后，提示框出现时会抖一抖
            //消失后的操作
            console.log("layer.msg()关闭了");
        });
    }
    //确认框
    function button2() {
        layer.confirm('are you sure?',
            {
                btn: ['好的', '不了'],//默认是 确认 取消
                title: '你真的确定吗？'
            },
            function() {layer.msg('你点击了确认')},
            function() {layer.msg('你取消了操作')});
    }
    //简单提示框类型的确认框
    function button3() {
        layer.msg('简单的确认框，不自动关闭', {
            time: 0, //不自动关闭
            btn: ['好勒', '丑拒'],
            yes: function(){
                layer.msg('温馨提醒您，您点击了【好勒】！');
            }
        });
    }
    //输入框
    function button4() {
        layer.prompt({
            title: '请输入，并确认',
            formType: 0 //0\其他:文本输入框，1:密码输入框，2:文本域
        }, function (content, index) {
            layer.msg('您输入了：' + content);
            //输入框需要手动关闭
            layer.close(index);
        });
    }
    //带图标的提示
    function button5() {
        layer.msg('不开心。。', {icon: 5});
    }
    //加载动画
    function button6() {
        layer.load(1);//参数为动画的样式，
        //此处关闭
        setTimeout(function(){
            layer.closeAll('loading');
        }, 2000);
    }
    //带文字的加载动画
    function button7() {
        //用带图标的提示来实现一个加载动画
        var index = layer.msg('加载中', {
            time: 0,
            icon: 16,
            shade: 0.1
        });
        //此处关闭
        setTimeout(function(){
            layer.close(index);
        }, 2000);
    }
    //tip
    function button8() {
        layer.tips('点击了', '#btn_tips', {
            //默认位置右，1：上，2：右，下：3，左：4
            tips: [3, '#d827a4'] //还可配置颜色
        });
    }
    //捕获页
    function button9() {
        layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            content: $('#div_show'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function(){
                layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 1000, icon:6});
            }
        });
    }
    //子页面
    function button10() {
        layer.open({
            type: 2,
            title: '标题',
            area:['600px','300px'],
            fixed: false,//是否固定
            maxmin: true,
            content: 'url',//子页面url
            end: function () {
                //页面关闭后的操作
            }
        });
    }
</script>
</html>
