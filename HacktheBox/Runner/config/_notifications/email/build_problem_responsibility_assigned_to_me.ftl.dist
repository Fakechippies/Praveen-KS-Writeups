<#-- Uses FreeMarker template syntax, template guide can be found at http://freemarker.org/docs/dgui.html -->

<#import "common.ftl" as common>
<#import "responsibility.ftl" as resp>
<#assign reporter>
  <@resp.reporterDescription responsibility/>
</#assign>

<#global subject>[<@common.subjMarker/>, INVESTIGATION] You are assigned for investigation of build problems</#global>

<#global body>You are assigned for investigation of build problems (${project.fullName}):
<@common.build_problem_list buildProblems/>

Assigned by ${reporter}
<@resp.removeMethod responsibility/>
<@resp.comment responsibility/>

${link.myResponsibilitiesLink}
<@common.footer/></#global>

<#global bodyHtml>
<div>
  <div>You are assigned for investigation of build problems (${project.fullName?html}):</div>
  <@common.build_problem_list_html buildProblems/>
  <div>Assigned by ${reporter?html}.</div>
  <div><@resp.removeMethod responsibility/></div>
  <div><@resp.comment responsibility/></div>
  <br>
  <div>More information on <a href='${link.myResponsibilitiesLink}'>investigations page</a>.</div>
  <@common.footerHtml/>
</div>
</#global>
