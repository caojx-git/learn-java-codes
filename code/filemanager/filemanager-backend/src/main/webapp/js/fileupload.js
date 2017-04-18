/**
 * Created by caojx on 17-4-18.
 */
$(document).ready(function () {

    var userInfo = {};

    var initParams = function () {
      $.ajax({
         url:"",
          type: "post",
          data: {
              codeType: "0002",
              codeId: ""
          },    
          success:function (data) {

          }
      });  
    };

    var initMultiSelect = function () {
        $('.searchable').multiSelect({
            selectableHeader: "<input type='text' class='search-input' autocomplete='on' placeholder='用户名'>",
            selectionHeader: "<input type='text' class='search-input' autocomplete='on' placeholder='用户名'>",
            afterInit: function(ms){
                var that = this,
                    $selectableSearch = that.$selectableUl.prev(),
                    $selectionSearch = that.$selectionUl.prev(),
                    selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
                    selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

                that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                    .on('keydown', function(e){
                        if (e.which === 40){
                            that.$selectableUl.focus();
                            return false;
                        }
                    });

                that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                    .on('keydown', function(e){
                        if (e.which == 40){
                            that.$selectionUl.focus();
                            return false;
                        }
                    });
            },
            afterSelect: function(){
                this.qs1.cache();
                this.qs2.cache();
            },
            afterDeselect: function(){
                this.qs1.cache();
                this.qs2.cache();
            }
        });
    }

    var initView = function () {
        initParams();
        initMultiSelect();
    };

    var initEvent = function () {

    };

    var init = function () {
        initView();
        initEvent();
    };

    init();

});