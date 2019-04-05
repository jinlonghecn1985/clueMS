package com.hnjing.utils.paginator.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.hnjing.utils.paginator.domain.PageBounds;


public class SybaseDialect extends Dialect{

    public SybaseDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
        super(mappedStatement, parameterObject, pageBounds);
    }


    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

}
