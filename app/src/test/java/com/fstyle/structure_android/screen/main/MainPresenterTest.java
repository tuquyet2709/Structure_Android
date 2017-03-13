package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
public class MainPresenterTest {

    private static final String USER_LOGIN_1 = "user_login_1";
    private static final String USER_LOGIN_2 = "user_login_2";

    @Mock
    MainContract.View mView;
    @Mock
    UserDataSource.LocalDataSource mLocalDataSource;
    @Mock
    UserDataSource.RemoteDataSource mRemoteDataSource;

    private UserRepository mUserRepository;
    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mUserRepository = new UserRepository(mLocalDataSource, mRemoteDataSource);
        mMainPresenter = new MainPresenter(mView, mUserRepository);
        mView.setPresenter(mMainPresenter);
    }

    @Test
    public void searchUsers() throws Exception {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));
        UsersList usersList = new UsersList(users);

        // When
        Mockito.when(mUserRepository.getRemoteDataSource().searchUsers(Mockito.anyString()))
                .thenReturn(Observable.just(usersList));

        // Then
        mMainPresenter.searchUsers(Mockito.anyString());

//        Mockito.verify(mView, Mockito.never()).showError(null);
//        Mockito.verify(mView).showListUser(usersList);

        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

        // When
        Mockito.when(mUserRepository.getRemoteDataSource().searchUsers(Mockito.anyString()))
                .thenReturn(Observable.<UsersList>error(throwable));

        // Then
        mMainPresenter.searchUsers(Mockito.anyString());

//        Mockito.verify(mView, Mockito.never()).showListUser(null);
//        Mockito.verify(mView).showError(throwable);
    }
}
