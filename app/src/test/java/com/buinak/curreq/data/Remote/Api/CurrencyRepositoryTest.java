package com.buinak.curreq.data.Remote.Api;

import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.data.Remote.CurrencyRepository;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class CurrencyRepositoryTest {

    @Test
    public void getInstance() {
        assertThat(CurrencyRepository.getInstance(), instanceOf(RemoteDataSource.class));
    }

}