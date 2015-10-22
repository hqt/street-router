<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/19/2015
  Time: 10:15 AM
  To change this template use File | Settings | File Templates.
--%>
<!-- BEGIN CONTENT -->
<div class="page-content-wrapper">
  <div class="page-content">
    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h4 class="modal-title">Modal title</h4>
          </div>
          <div class="modal-body">
            Widget settings form goes here
          </div>
          <div class="modal-footer">
            <button type="button" class="btn blue">Save changes</button>
            <button type="button" class="btn default" data-dismiss="modal">Close</button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <!-- BEGIN STYLE CUSTOMIZER -->
    <div class="theme-panel hidden-xs hidden-sm">
      <div class="toggler tooltips" data-container="body" data-placement="left" data-html="true"
           data-original-title="Click to open advance theme customizer panel">
        <i class="icon-settings"></i>
      </div>
      <div class="toggler-close">
        <i class="icon-close"></i>
      </div>
      <div class="theme-options">
        <div class="theme-option theme-colors clearfix">
							<span>
							THEME COLOR </span>
          <ul>
            <li class="color-black current color-default" data-style="default">
            </li>
            <li class="color-blue" data-style="blue">
            </li>
            <li class="color-brown" data-style="brown">
            </li>
            <li class="color-purple" data-style="purple">
            </li>
            <li class="color-grey" data-style="grey">
            </li>
            <li class="color-white color-light" data-style="light">
            </li>
          </ul>
        </div>
        <div class="theme-option">
							<span>
							Layout </span>
          <select class="layout-option form-control input-small">
            <option value="fluid" selected="selected">Fluid</option>
            <option value="boxed">Boxed</option>
          </select>
        </div>
        <div class="theme-option">
							<span>
							Header </span>
          <select class="page-header-option form-control input-small">
            <option value="fixed" selected="selected">Fixed</option>
            <option value="default">Default</option>
          </select>
        </div>
        <div class="theme-option">
							<span>
							Sidebar </span>
          <select class="sidebar-option form-control input-small">
            <option value="fixed">Fixed</option>
            <option value="default" selected="selected">Default</option>
          </select>
        </div>
        <div class="theme-option">
							<span>
							Footer </span>
          <select class="page-footer-option form-control input-small">
            <option value="fixed">Fixed</option>
            <option value="default" selected="selected">Default</option>
          </select>
        </div>
      </div>
    </div>
    <!-- END BEGIN STYLE CUSTOMIZER -->
    <!-- BEGIN PAGE HEADER-->
    <h3 class="page-title">
      Route
      <small>showing route information</small>
    </h3>
    <div class="page-bar">
      <ul class="page-breadcrumb">
        <li>
          <i class="fa fa-home"></i>
          <a href="index.html">Home</a>
          <i class="fa fa-angle-right"></i>
        </li>
        <li>
          <a href="#">Route</a>
          <i class="fa fa-angle-right"></i>
        </li>
        <li>
          <a href="#">Route Information</a>
        </li>
      </ul>
      <div class="page-toolbar">
        <div class="btn-group pull-right">
          <button type="button" class="btn btn-fit-height grey-salt dropdown-toggle"
                  data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
                  data-close-others="true">
            Actions <i class="fa fa-angle-down"></i>
          </button>
          <ul class="dropdown-menu pull-right" role="menu">
            <li>
              <a href="#">Action</a>
            </li>
            <li>
              <a href="#">Another action</a>
            </li>
            <li>
              <a href="#">Something else here</a>
            </li>
            <li class="divider">
            </li>
            <li>
              <a href="#">Separated link</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <div class="row">
      <div class="col-md-12">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="portlet box purple">
          <div class="portlet-title">
            <div class="caption">
              <i class="fa fa-comments"></i>Striped Table
            </div>
            <div class="tools">
              <a href="javascript:;" class="collapse">
              </a>
              <a href="#portlet-config" data-toggle="modal" class="config">
              </a>
              <a href="javascript:;" class="reload">
              </a>
              <a href="javascript:;" class="remove">
              </a>
            </div>
          </div>
          <%--<div class="table-scrollable">
            &lt;%&ndash;table class="table table-striped table-bordered table-hover dataTable no-footer"
                                   id="datatable_ajax" role="grid" aria-describedby="datatable_ajax_info">
                                <thead>
                                <tr class="heading" role="row">
                                    <th width="2%" class="sorting_disabled" rowspan="1" colspan="1">
                                        <div class="checker"><span><input class="group-checkable"
                                                                          type="checkbox"></span></div>
                                    </th>
                                    <th width="5%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Record&nbsp;#
                                    </th>
                                    <th width="15%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Date
                                    </th>
                                    <th width="15%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Customer
                                    </th>
                                    <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Ship&nbsp;To
                                    </th>
                                    <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Price
                                    </th>
                                    <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Amount
                                    </th>
                                    <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Status
                                    </th>
                                    <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                        rowspan="1" colspan="1">
                                        Actions
                                    </th>
                                </tr>
                                <tr class="filter" role="row">
                                    <td rowspan="1" colspan="1">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <input name="order_id" class="form-control form-filter input-sm"
                                               type="text">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <div class="input-group date date-picker margin-bottom-5"
                                             data-date-format="dd/mm/yyyy">
                                            <input name="order_date_from" class="form-control form-filter input-sm"
                                                   type="text" readonly="" placeholder="From">
                                            <span class="input-group-btn">
                                            <button class="btn btn-sm default" type="button"><i
                                                    class="fa fa-calendar"></i></button>
                                            </span>
                                        </div>
                                        <div class="input-group date date-picker" data-date-format="dd/mm/yyyy">
                                            <input name="order_date_to" class="form-control form-filter input-sm"
                                                   type="text" readonly="" placeholder="To">
                                            <span class="input-group-btn">
                                            <button class="btn btn-sm default" type="button"><i
                                                    class="fa fa-calendar"></i></button>
                                            </span>
                                        </div>
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <input name="order_customer_name" class="form-control form-filter input-sm"
                                               type="text">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <input name="order_ship_to" class="form-control form-filter input-sm"
                                               type="text">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <div class="margin-bottom-5">
                                            <input name="order_price_from" class="form-control form-filter input-sm"
                                                   type="text" placeholder="From">
                                        </div>
                                        <input name="order_price_to" class="form-control form-filter input-sm"
                                               type="text" placeholder="To">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <div class="margin-bottom-5">
                                            <input name="order_quantity_from"
                                                   class="form-control form-filter input-sm margin-bottom-5 clearfix"
                                                   type="text" placeholder="From">
                                        </div>
                                        <input name="order_quantity_to" class="form-control form-filter input-sm"
                                               type="text" placeholder="To">
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <select name="order_status" class="form-control form-filter input-sm">
                                            <option value="">Select...</option>
                                            <option value="pending">Pending</option>
                                            <option value="closed">Closed</option>
                                            <option value="hold">On Hold</option>
                                            <option value="fraud">Fraud</option>
                                        </select>
                                    </td>
                                    <td rowspan="1" colspan="1">
                                        <div class="margin-bottom-5">
                                            <button class="btn btn-sm yellow filter-submit margin-bottom"><i
                                                    class="fa fa-search"></i> Search
                                            </button>
                                        </div>
                                        <button class="btn btn-sm red filter-cancel"><i class="fa fa-times"></i>
                                            Reset
                                        </button>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>&ndash;%&gt;
          </div>--%>
          <div class="portlet-body" id="datatable_ajax">
            <div class="table-scrollable">
              <table class="table table-striped table-hover">
                <thead>
                <tr>
                  <th>
                    #
                  </th>
                  <th>
                    Station Name
                  </th>
                  <th>
                    Action
                  </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="station" items="${stations}" varStatus="count">
                  <tr>
                    <td>${count.count}</td>
                    <td>${station.stationName}</td>
                    <td>
                      <div class="btn-group">
                        <button type="button" class="btn blue">
                          <i class="fa fa-edit"></i>
                          Edit
                        </button>
                        <button type="button" class="btn red">
                          <i class="fa fa-times"></i>
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <%--<a title="Next" class="btn btn-sm default next" onclick="loadRoutesAtPage()"><i class="fa fa-angle-right"></i></a>--%>
            </div>
          </div>
        </div>
        <!-- END SAMPLE TABLE PORTLET-->
      </div>
    </div>


    <!-- END PAGE CONTENT-->
  </div>
</div>
<!-- END CONTENT -->
