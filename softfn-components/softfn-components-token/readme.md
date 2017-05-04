TOKEN模块
----------

消费者:

    1）POM引入
    <!--令牌校验-->
    <dependency>
        <groupId>com.softfn.dev</groupId>
        <artifactId>softfn-components-token</artifactId>
    </dependency>
    
    2）token验证使用：
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @InvokeLog(name = "调用createBlank", description = "创建空白问卷")
    @TokenHandler(validate = true)
    @ResponseBody
    public BaseResponse<String> createBlank(@ParamToken String token) {
        // todo
        ...
    }

    3）需要实现：
    <!-- 项目中需实现接口 com.softfn.dev.common.interfaces.TokenService -->
    <bean id="tokenService" class="XXX.TokenServiceImpl"/>