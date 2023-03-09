"use strict";(self["webpackChunknas_crontab_manager"]=self["webpackChunknas_crontab_manager"]||[]).push([[443],{6955:function(e,l,a){a.d(l,{BT:function(){return i},EO:function(){return d},Os:function(){return m},U_:function(){return r},g2:function(){return u}});var t=a(9669),n=a.n(t);const o="/cron";async function r(){return await n().get(o+"/common/task/type")}async function i(){return await n().get(o+"/common/task/compare")}async function u(){return await n().get(o+"/common/task/email/keyword")}async function m(){return await n().get(o+"/system")}async function d(e){return await n().post(o+"/system",e)}},6685:function(e,l,a){a.r(l),a.d(l,{default:function(){return x}});var t={};a.r(t),a.d(t,{cleanLog:function(){return v},getCronAnalysis:function(){return V},getLog:function(){return U},getLogList:function(){return L}});var n=a(6252),o=a(3577);const r=e=>((0,n.dD)("data-v-6463ec24"),e=e(),(0,n.Cn)(),e),i=r((()=>(0,n._)("span",null,"ms",-1))),u={key:0,style:{color:"#33c800"}},m={key:1,style:{color:"#909399"}},d={key:2,style:{color:"red"}},s={key:0,style:{color:"#33c800"}},c={key:1,style:{color:"#909399"}},g={key:2,style:{color:"red"}},p=["innerHTML"],w=["innerHTML"],f=["innerHTML"],b=["innerHTML"];function y(e,l,a,t,r,y){const _=(0,n.up)("el-button"),W=(0,n.up)("basic-search"),h=(0,n.up)("el-table-column"),k=(0,n.up)("basic-table"),T=(0,n.up)("page-format"),L=(0,n.up)("el-descriptions-item"),U=(0,n.up)("el-descriptions"),V=(0,n.up)("el-dialog"),v=(0,n.Q2)("loading");return(0,n.wg)(),(0,n.iD)(n.HY,null,[(0,n.Wm)(T,{title:"任务日志"},{"title-right":(0,n.w5)((()=>[(0,n.Wm)(_,{onClick:l[0]||(l[0]=l=>e.$util.goBack())},{default:(0,n.w5)((()=>[(0,n.Uk)("返回")])),_:1}),(0,n.Wm)(_,{type:"primary",onClick:t.cleanLog},{default:(0,n.w5)((()=>[(0,n.Uk)("清空日志")])),_:1},8,["onClick"])])),default:(0,n.w5)((()=>[(0,n.wy)(((0,n.wg)(),(0,n.j4)(k,{ref:"basicTable",column:t.column,getList:t.request.getLogList,query:t.query,defaultParam:t.defaultParam},{"basic-table-top":(0,n.w5)((()=>[(0,n.Wm)(W,{query:t.query,"onUpdate:query":l[1]||(l[1]=e=>t.query=e),search:t.search},null,8,["query","search"])])),runningTimer:(0,n.w5)((({row:e})=>[(0,n.Uk)((0,o.zw)(e.runningTimer?e.runningTimer:0),1),i])),startTime:(0,n.w5)((({row:l})=>[(0,n.Uk)((0,o.zw)(e.crtTimeFtt(l.startTime)),1)])),endTime:(0,n.w5)((({row:l})=>[(0,n.Uk)((0,o.zw)(e.crtTimeFtt(l.endTime)),1)])),executeCode:(0,n.w5)((({row:e})=>[1===e.executeCode?((0,n.wg)(),(0,n.iD)("span",u,"success")):0===e.executeCode?((0,n.wg)(),(0,n.iD)("span",m,"running")):((0,n.wg)(),(0,n.iD)("span",d,"error"))])),default:(0,n.w5)((()=>[(0,n.Wm)(h,{align:"center",label:"操作",width:"150"},{default:(0,n.w5)((({row:e})=>[(0,n.Wm)(_,{type:"text",onClick:l=>t.detail(e)},{default:(0,n.w5)((()=>[(0,n.Uk)("详情")])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["column","getList","query","defaultParam"])),[[v,t.loading]])])),_:1}),(0,n.Wm)(V,{modelValue:t.dialogTableVisible,"onUpdate:modelValue":l[2]||(l[2]=e=>t.dialogTableVisible=e),title:"日志详情",width:"70%"},{default:(0,n.w5)((()=>[(0,n.Wm)(U,{direction:"horizontal",column:2,border:"",width:"150px"},{default:(0,n.w5)((()=>[(0,n.Wm)(L,{label:"taskId","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.id),1)])),_:1}),(0,n.Wm)(L,{label:"taskName","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.taskName),1)])),_:1}),(0,n.Wm)(L,{label:"description","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.description||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"cron","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.cron),1)])),_:1}),(0,n.Wm)(L,{label:"taskType","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.taskType),1)])),_:1}),(0,n.Wm)(L,{label:"runningTimer","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.runningTimer?t.detailLog.runningTimer:0)+"ms",1)])),_:1}),(0,n.Wm)(L,{label:"executeCode","label-align":"right"},{default:(0,n.w5)((()=>[1===t.detailLog.executeCode?((0,n.wg)(),(0,n.iD)("span",s,"success")):0===e.row.executeCode?((0,n.wg)(),(0,n.iD)("span",c,"running")):((0,n.wg)(),(0,n.iD)("span",g,"error"))])),_:1}),(0,n.Wm)(L,{label:"startTime","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(e.crtTimeFtt(t.detailLog.startTime)),1)])),_:1}),(0,n.Wm)(L,{label:"endTime","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(e.crtTimeFtt(t.detailLog.endTime)),1)])),_:1}),(0,n.Wm)(L,{label:"timeout","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.timeout||!1),1)])),_:1}),(0,n.Wm)(L,{label:"executeTimeout","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.executeTimeout||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"command","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n._)("div",{style:{"white-space":"pre-line"},innerHTML:t.detailLog.command},null,8,p)])),_:1}),(0,n.Wm)(L,{label:"params","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n._)("div",{style:{"white-space":"pre-line"},innerHTML:t.detailLog.params},null,8,w)])),_:1}),(0,n.Wm)(L,{label:"compareType","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.compareType||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"compareIgnoreCase","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(null==t.detailLog.compareIgnoreCase?"-":t.detailLog.compareIgnoreCase),1)])),_:1}),(0,n.Wm)(L,{label:"expectedValue","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.expectedValue||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"email","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.email||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"emailSuccessMessage","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.emailSuccessMessage||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"emailErrorMessage","label-align":"right"},{default:(0,n.w5)((()=>[(0,n.Uk)((0,o.zw)(t.detailLog.emailErrorMessage||"-"),1)])),_:1}),(0,n.Wm)(L,{label:"emailResult","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n._)("div",{style:{"white-space":"pre-line"},innerHTML:t.detailLog.emailResult},null,8,f)])),_:1}),(0,n.Wm)(L,{label:"result","label-align":"right",span:2},{default:(0,n.w5)((()=>[(0,n._)("div",{style:{"white-space":"pre-line"},innerHTML:t.detailLog.executeResult},null,8,b)])),_:1})])),_:1})])),_:1},8,["modelValue"])],64)}a(7658);var _=a(2262),W=a(2201),h=a(9669),k=a.n(h);const T="/cron";async function L(e){return await k().get(T+"/log",{params:e})}async function U(e){return await k().get(T+"/log/"+e)}async function V(e){return await k().get(T+"/task/cron/analysis",{params:e})}async function v(e){return await k().put(T+"/log/clean/"+e)}const H=e=>{let l=new Date(e),a=l.getFullYear(),t=l.getMonth()+1<10?"0"+(l.getMonth()+1):l.getMonth()+1,n=l.getDate()<10?"0"+l.getDate():l.getDate(),o=l.getHours()<10?"0"+l.getHours():l.getHours(),r=l.getMinutes()<10?"0"+l.getMinutes():l.getMinutes(),i=l.getSeconds()<10?"0"+l.getSeconds():l.getSeconds();return a+"-"+t+"-"+n+" "+o+":"+r+":"+i};var C={crtTimeFtt:H},z=a(7234),M={name:"Log",components:{},setup(){let e=(0,W.yj)().params.id||"",l=(0,_.iH)(!1),a=(0,_.iH)({}),o=(0,_.iH)(null),r=(0,_.iH)(!1),i=(0,_.iH)({}),u=(0,_.iH)({time:{type:"daterange",label:"执行周期",value:[]}}),m=(0,_.iH)({taskId:(0,W.yj)().params.id||""});const d=(0,W.tv)(),s=[{label:"任务名称",prop:"taskName"},{label:"cron表达式",prop:"cron"},{label:"任务类型",prop:"taskType"},{label:"开始时间",prop:"startTime"},{label:"结束时间",prop:"endTime"},{label:"耗时",prop:"runningTimer"},{label:"执行结果",prop:"executeCode"}],c=async e=>{let t=await U(e.id);a.value=t.data,l.value=!0},g=async()=>{r.value=!0;let l=await v(e);1===l.code&&(0,z.z8)({message:"操作成功！",type:"success"}),r.value=!1,d.push("/task")};return{column:s,request:t,...C,loading:r,basicTable:o,router:d,detail:c,dialogTableVisible:l,defineComponent:n.aZ,detailLog:a,cleanLog:g,taskId:e,query:i,search:u,defaultParam:m}}},D=a(3744);const P=(0,D.Z)(M,[["render",y],["__scopeId","data-v-6463ec24"]]);var x=P},4433:function(e,l,a){a.r(l),a.d(l,{default:function(){return s}});var t=a(6252);function n(e,l,a,n,o,r){const i=(0,t.up)("el-button"),u=(0,t.up)("el-input"),m=(0,t.up)("el-form-item"),d=(0,t.up)("el-col"),s=(0,t.up)("el-row"),c=(0,t.up)("el-option"),g=(0,t.up)("el-select"),p=(0,t.up)("el-form"),w=(0,t.up)("page-format");return(0,t.wg)(),(0,t.j4)(w,{title:"系统配置"},{"title-right":(0,t.w5)((()=>[(0,t.Wm)(i,{type:"primary",onClick:n.updateSystem},{default:(0,t.w5)((()=>[(0,t.Uk)("编辑")])),_:1},8,["onClick"])])),default:(0,t.w5)((()=>[(0,t.Wm)(p,{ref:"ruleForm","label-position":"right","label-width":"auto",model:n.form,rules:n.rules},{default:(0,t.w5)((()=>[(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:16},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"系统名称",prop:"name"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.subject,"onUpdate:modelValue":l[0]||(l[0]=e=>n.form.subject=e),placeholder:"请输入系统名称"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:16},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"日志保留时间(天)",prop:"logRetainDays"},{default:(0,t.w5)((()=>[(0,t.Wm)(g,{modelValue:n.form.logRetainDays,"onUpdate:modelValue":l[1]||(l[1]=e=>n.form.logRetainDays=e),placeholder:"请选择日志保留时间"},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(n.logRetainDayArrays,(e=>((0,t.wg)(),(0,t.j4)(c,{key:e,label:e,value:e},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:16},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"命令行前缀",prop:"commandPrefix"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.commandPrefix,"onUpdate:modelValue":l[2]||(l[2]=e=>n.form.commandPrefix=e),placeholder:"请输入命令行前缀"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:16},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"源码路径",prop:"codePath"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.codePath,"onUpdate:modelValue":l[3]||(l[3]=e=>n.form.codePath=e),placeholder:"请输入源码路径"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:16},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"邮箱发件箱",prop:"emailFromAddress"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.emailFromAddress,"onUpdate:modelValue":l[4]||(l[4]=e=>n.form.emailFromAddress=e),placeholder:"请输入邮箱发送地址"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:8},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"SMTP邮箱服务器",prop:"emailHost"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.emailHost,"onUpdate:modelValue":l[5]||(l[5]=e=>n.form.emailHost=e),placeholder:"请输入SMTP邮箱服务器"},null,8,["modelValue"])])),_:1})])),_:1}),(0,t.Wm)(d,{span:8},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"SMTP邮箱端口",prop:"emailPort"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.emailPort,"onUpdate:modelValue":l[6]||(l[6]=e=>n.form.emailPort=e),placeholder:"请输入邮箱端口"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),(0,t.Wm)(s,null,{default:(0,t.w5)((()=>[(0,t.Wm)(d,{span:8},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"邮箱账户",prop:"emailAccount"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{modelValue:n.form.emailAccount,"onUpdate:modelValue":l[7]||(l[7]=e=>n.form.emailAccount=e),placeholder:"请输入邮箱账号"},null,8,["modelValue"])])),_:1})])),_:1}),(0,t.Wm)(d,{span:8},{default:(0,t.w5)((()=>[(0,t.Wm)(m,{label:"邮箱密码",prop:"emailPassword"},{default:(0,t.w5)((()=>[(0,t.Wm)(u,{"show-password":"",modelValue:n.form.emailPassword,"onUpdate:modelValue":l[8]||(l[8]=e=>n.form.emailPassword=e),placeholder:"请输入邮箱密码"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1})}var o=a(2262),r=a(6955),i=a(7234),u={name:"System",props:{visible:{type:Boolean,default:!1}},setup(e){let l=(0,o.iH)([1,3,7,15,30,90,180,365]),a=(0,o.iH)({}),n=(0,o.iH)(null),u=(0,o.qj)({subject:[{required:!0,message:"请填写名称"}],logRetainDayArrays:[{required:!0,message:"请填写日志保留时间"}]}),m=(0,o.iH)(!1);async function d(){let e=await r.Os();a.value=e.data||{}}(0,t.bv)((async()=>{d()}));let s=(0,t.Fl)((()=>e.visible));const c=async()=>{console.log(n.value),n.value.validate((async e=>{if(e){let e=await r.EO(a.value);1===e.code&&((0,i.z8)({showClose:!0,message:"操作成功",type:"success"}),m.value=!1)}}))};return{loading:m,drawer:s,form:a,rules:u,logRetainDayArrays:l,updateSystem:c,ruleForm:n}}},m=a(3744);const d=(0,m.Z)(u,[["render",n],["__scopeId","data-v-059529be"]]);var s=d}}]);