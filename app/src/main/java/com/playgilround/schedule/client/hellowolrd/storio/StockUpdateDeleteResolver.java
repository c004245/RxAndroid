package com.playgilround.schedule.client.hellowolrd.storio;

import androidx.annotation.NonNull;

import com.playgilround.schedule.client.hellowolrd.util.StockUpdate;
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

public class StockUpdateDeleteResolver extends DefaultDeleteResolver<StockUpdate> {

    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull StockUpdate object) {
        return DeleteQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.Columns.ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }
}
