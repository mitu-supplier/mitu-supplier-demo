<!--分页-->
<template>
	<div class="pagination" v-if="totalPage>1">
		<span v-if="!small" class="total">共{{totalCount}}条记录<!--  第{{currentPage}}/{{totalPage}}页--></span>
		<ul class="paging">
			<li class="prev" :class="{'disabled':currentPage<=1}" @click="currentPage<=1?'':turn(currentPage-1)"><i class="iconfont icon-chevron-left"></i></li>

			<li v-for="i in showPageBtn"
				:key="i" class="num"
				:class="{'active':i==currentPage,'num iconfont icon-more':i==='-'||i==='+','icon-d-arrow-left':toPrev&&i==='-','icon-d-arrow-right':toNext&&i==='+'}"
				@mouseenter="i==='-'?toPrev=true:i==='+'?toNext=true:''"
				@mouseleave="i==='-'?toPrev=false:i==='+'?toNext=false:''"
				@click="i==='-'?turn(currentPage-1):i==='+'? turn(currentPage+1):turn(i)">{{i>0?i:''}}</li>
			<!--<li v-else-if="i==='-'" class="num iconfont icon-more" :class="{'icon-d-arrow-left':toPrev}" @click="turn(currentPage-1)" @mouseenter="toPrev=true" @mouseleave="toPrev=false"></li>
			<li v-else-if="i==='+'" class="num iconfont icon-more" :class="{'icon-d-arrow-right':toNext}" @click="turn(currentPage+1)" @mouseenter="toNext=true" @mouseleave="toNext=false"></li>
-->

			<li class="next" :class="{'disabled':currentPage>=totalPage}"  @click="currentPage>=totalPage?'':turn(currentPage+1)"><i class="iconfont icon-chevron-right"></i></li>
		</ul>
		<select v-if="!small" v-model="limitNum" @change="turn(currentPage)">
			<option v-for="item in limitNums" :key="item" :value="item">{{item}}条/页</option>
		</select>
		<span  v-if="all" class="jump">前往<input type="number" autocomplete="off" min="1" :max="totalPage" v-model="goPage" @keyup.enter="turn(goPage)">页</span>
	</div>
</template>

<script>
export default {
  name: "MyPagination",
  props: {
    currentPage: {
      //当前页
      type: Number,
      default: 1
    },
    limit: {
      //每页显示条数
      type: Number,
      default: 10
    },
    totalCount: {
      //总条数
      type: Number,
      required: true
    },
    small: {
      type: Boolean,
      default: false
    },
    all: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      goPage: "",
      limitNum: "",
      limitNums: [5, 10, 15, 20, 25, 30],
      toPrev: false,
      toNext: false
    };
  },
  created: function() {
    this.initLimitNums();
  },
  computed: {
    totalPage() {
      return Math.ceil(this.totalCount / this.limit);
    },
    showPageBtn() {
      let pageNum = Number(this.totalPage),
        index = Number(this.currentPage),
        arr = [];
      if (pageNum <= 9) {
        for (let i = 1; i <= pageNum; i++) {
          arr.push(i);
        }
        return arr;
      }
      if (index < 5) return [1, 2, 3, 4, 5, 6, 7, "+", pageNum];
      if (index >= pageNum - 1)
        return [
          1,
          2,
          "-",
          pageNum - 5,
          pageNum - 4,
          pageNum - 3,
          pageNum - 2,
          pageNum - 1,
          pageNum
        ];
      if (index === pageNum - 2)
        return [
          1,
          2,
          "-",
          pageNum - 5,
          pageNum - 4,
          pageNum - 3,
          pageNum - 2,
          pageNum - 1,
          pageNum
        ];
      return [
        1,
        "-",
        index - 2,
        index - 1,
        index,
        index + 1,
        index + 2,
        "+",
        pageNum
      ];
    }
  },
  methods: {
    initLimitNums() {
      this.limitNum = this.limit;
      //if (this.limitNums.indexOf(this.limit) == -1) {
      if (!this.limitNums.includes(this.limit)) {
        this.limitNums.push(this.limit);
        this.limitNums.sort(function(a, b) {
          return a - b;
        });
      }
    },
    //翻页，显示条数变化
    turn(page) {
      let i = parseInt(Number(page));
      if (i < 1) {
        i = 1;
      } else if (i > this.totalPage) {
        i = this.totalPage;
      }
      if (this.limitNum !== this.limit && this.limitNum !== "") {
        //每页显示条数改变
        let pages = Math.ceil(this.totalCount / this.limitNum);
        if (page > pages) {
          i = pages;
        }
        this.$emit("update:limit", this.limitNum);
      }
      this.$emit("update:currentPage", i);
      this.$emit("turn");
    }
  }
};
</script>