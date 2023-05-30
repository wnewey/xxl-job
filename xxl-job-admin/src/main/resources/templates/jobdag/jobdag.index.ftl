<!DOCTYPE html>
<html>
<head>
    <#import "../common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <title>${I18n.admin_name}</title>
    <script src="${request.contextPath}/static/dagre-d3/d3.v5.min.js"></script>
    <script src="${request.contextPath}/static/dagre-d3/dagre-d3.min.js"></script>
    <style>
      .content-dag {
        position: fixed;
        top: 100px;
        bottom: 50px;
        left: 230px;
        right: 0;
        margin: 0;
        padding: 0;
        /* background: #333; */
      }

      .live.map {
        width: 100%;
        height: 100%;
      }

      svg {
        width: 100%;
        height: 100%;
        overflow: hidden;
      }

      .live.map text {
        font-weight: 300;
        font-size: 14px;
      }

      .live.map .node rect {
        stroke-width: 1.5px;
        stroke: #bbb;
        fill: #fff;
      }

      .live.map .status {
        height: 100%;
        width: 15px;
        display: block;
        float: left;
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
        margin-right: 4px;
      }

      .live.map .running .status {
        background-color: #7f7;
      }

      .live.map .stopped .status {
        background-color: #f77;
      }

      .live.map .node g div {
        width: 200px;
        height: 40px;
        color: #333;
      }

      .live.map .node g div span {
        display: block;
        text-align: left;
      }

      .live.map .node g div span.name {
        padding-top: 10px;
      }
      .live.map .node g div span.desc {
        margin-top: 5px;
      }
      .live.map .edgeLabel text {
        width: 50px;
        fill: #fff;
      }

      .live.map .edgePath path {
        stroke: #999;
        stroke-width: 1.5px;
        fill: #999;
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
        <section class="content-dag">
            <div class="live map">
                <svg>
                    <g/>
                </svg>
            </div>
        </section>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<script>
  var graphInfo = ${graphstr};
  var svg = d3.select("svg"),
    inner = svg.select("g"),
    zoom = d3.zoom().on("zoom", function () {
      inner.attr("transform", d3.event.transform);
    });
  svg.call(zoom);

  var render = new dagreD3.render();

  // Left-to-right layout
  var g = new dagreD3.graphlib.Graph({ compound: true });
  g.setGraph({
    nodesep: 70,
    ranksep: 50,
    rankdir: graphInfo.direction,
    marginx: 20,
    marginy: 20,
  });

  for (var i in graphInfo.subGraphs) {
    var nodes = graphInfo.subGraphs[i].nodes;
    var group = "group_" + i;
    g.setNode(group, {
      label: graphInfo.subGraphs[i].name,
      clusterLabelPos: "top",
      style: "fill: #d3d7e8",
    });
    for (var j in nodes) {
      var node = nodes[j];

      var className = node.active ? "running" : "stopped";

      var html = "<div>";
      html += "<span class=status></span>";
      html += "<span class=name>" + node.id + " " + node.desc + "</span>";
      html += "<span class=desc>" + node.job.jobCron + "</span>";
      html += "</div>";
      g.setNode(node.id, {
        labelType: "html",
        label: html,
        rx: 5,
        ry: 5,
        padding: 0,
        class: className,
      });
      g.setParent(node.id, group);
    }
  }

  for (var k in graphInfo.flowList) {
    var flow = graphInfo.flowList[k];
    g.setEdge(flow.start.id, flow.stop.id, {
      // label: worker.inputThroughput + "/s",
      width: 40,
    });
  }

  inner.call(render, g);

</script>
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="${request.contextPath}/static/js/jobgroup.index.1.js"></script>
</body>
</html>
