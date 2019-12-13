<!DOCTYPE html>
<html>
<head>
    <#import "../common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <title>${I18n.admin_name}</title>
    <script src="${request.contextPath}/static/mermaid/mermaid.8.4.3.min.js"></script>
    <script>
      document.onreadystatechange = function () {
        if (document.readyState === 'complete') {
          document.querySelector(".dag-content").style.display = "block"
        }
      }

      function nodeCallback (e) {
        layer.msg("点击了任务[" + e + "]");
      }
    </script>
    <style>
        div.mermaid {
            font-size: 16px;
        }

        .node.active .label {
            color: #fff !important;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "jobdag" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>${I18n.jobdag_name}</h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 dag-content" style="display: none">
                        <div class="mermaid">${graphstr}</div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<script>
  var config = {
    startOnLoad: true,
    securityLevel: 'loose',
    theme: "neutral",
    flowchart: {
      useMaxWidth: true,
      htmlLabels: true
    }
  };
  mermaid.initialize(config);
</script>
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="${request.contextPath}/static/js/jobgroup.index.1.js"></script>
</body>
</html>
