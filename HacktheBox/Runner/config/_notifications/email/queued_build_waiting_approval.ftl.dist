<#-- Uses FreeMarker template syntax, template guide can be found at http://freemarker.org/docs/dgui.html -->

<#import "common.ftl" as common>

<#global subject>[<@common.subjMarker/>, APPROVAL REQUIRED] Build ${project.fullName} / ${buildType.name} <@common.short_queued_build_info queuedBuild/></#global>

<#global body>
  Build in ${project.fullName} / ${buildType.name} <@common.short_queued_build_info queuedBuild/> requires approval (triggered by ${queuedBuild.triggeredBy.asString}).

  Build overview: ${link.buildLink}

  Build changes: ${link.buildChangesLink}
  Build dependencies: ${link.buildDependenciesLink}
  <@common.footer/>
</#global>

<#global bodyHtml>
  <div>
    <div>
      <a href='${link.buildLink}'>Build in <b>${project.fullName?html} / ${buildType.name?html}</b> </a><@common.short_queued_build_info_html queuedBuild/> requires approval
      (triggered by ${queuedBuild.triggeredBy.asString?html})
    </div>
    <a href='${link.buildChangesLink}'>Build changes</a>
    <br>
    <a href='${link.buildDependenciesLink}'>Build dependencies</a>
    <br>
      <@common.footerHtml/>
  </div>
</#global>
