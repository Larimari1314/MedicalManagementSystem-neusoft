package com.max.back.common.sercurity.intercept;

/*

@Component
@Slf4j
publicclassAuthInterceptorimplementsHandlerInterceptor{
	@Autowired
	privateHttpJwtTokenUtiljwtHttpUtil;
	@Override
	publicbooleanpreHandle(HttpServletRequestrequest,HttpServletResponseresponse,Objecthandler)
			throwsException{
		log.info(request.getRequestURI()+"isstart");
		if(!(handlerinstanceofHandlerMethod)){
			returntrue;
		
HandlerMethodhandlerMethod=(HandlerMethod)handler;
AnnotationauthIgrone=handlerMethod.getMethodAnnotation(AuthIgrone.class);
//不需要认证
if(authIgrone!=null){
	returntrue;

booleanres=jwtHttpUtil.validate(request);
		//权限不足
		if(!res){
			ResponseResult<Object>rrs=ResponseResult.getMessageResult(null,"E001",StatusCode.C405);
			response.getWriter().write(JSONUtil.toJsonStr(rrs));
			returnfalse;
		else{
			returntrue;
		
	

*/





