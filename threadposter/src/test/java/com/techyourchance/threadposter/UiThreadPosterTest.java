package com.techyourchance.threadposter;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UiThreadPosterTest {

    private Handler mUiHandlerMock;
    private UiThreadPoster SUT;

    @Before
    public void setup() throws Exception {
        mUiHandlerMock = mock(Handler.class);
        SUT = new UiThreadPoster() {
            @Override
            protected Handler getMainHandler() {
                return mUiHandlerMock;
            }
        };
    }


    @Test
    public void execute_delegatesToUiHandler() throws Exception {
        // Arrange
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // no-op
            }
        };
        ArgumentCaptor<Runnable> ac = ArgumentCaptor.forClass(Runnable.class);
        // Act
        SUT.post(runnable);
        // Assert
        verify(mUiHandlerMock, times(1)).post(ac.capture());
        assertThat(ac.getValue(), is(runnable));
    }
}