<template>
    <div>
        <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
        >
        </el-table>
    </div>
</template>

<script>
    export default {
        name: 'BuySellRecordList',
        data: function() {
            return {}
        }
    };
</script>

<style scoped>

</style>
