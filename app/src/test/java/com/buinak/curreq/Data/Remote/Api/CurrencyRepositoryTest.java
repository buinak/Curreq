package com.buinak.curreq.Data.Remote.Api;

import com.buinak.curreq.Data.Remote.CurrencyDataSource;
import com.buinak.curreq.Data.Remote.CurrencyRepository;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class CurrencyRepositoryTest {

    @Test
    public void getInstance() {
        assertThat(CurrencyRepository.getInstance(), instanceOf(CurrencyDataSource.class));
    }

}