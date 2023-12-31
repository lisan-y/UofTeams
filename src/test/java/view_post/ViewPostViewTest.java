package view_post;

import delete_post.UI.DeleteView;
import delete_post.drivers.DeletePostDataAccess;
import delete_post.interface_adapters.DeletePostController;
import delete_post.interface_adapters.DeletePostPresenter;
import delete_post.interface_adapters.DeletePostViewModel;
import delete_post.use_case.*;
import favourite.drivers.FavouriteDatabaseAccess;
import favourite.interface_adapters.FavouriteController;
import favourite.interface_adapters.FavouritePresenter;
import favourite.interface_adapters.FavouriteViewModel;
import favourite.ui.FavouriteView;
import favourite.use_case.*;
import favourite.use_case.FavouritePostReaderInterface;
import filter_post.drivers.FilterPostDataAccess;
import filter_post.interface_adapters.FilterPostController;
import filter_post.interface_adapters.FilterPostPresenter;
import filter_post.interface_adapters.FilterPostViewModel;
import filter_post.ui.FilterPostBarView;
import filter_post.use_case.FilterPostDsGateway;
import filter_post.use_case.FilterPostInputBoundary;
import filter_post.use_case.FilterPostInteractor;
import filter_post.use_case.FilterPostOutputBoundary;
import make_comment.driver.MakeCommentDatabaseAccess;
import make_comment.interface_adapter.MakeCommentController;
import make_comment.interface_adapter.MakeCommentPresenter;
import make_comment.interface_adapter.MakeCommentViewModel;
import make_comment.ui.MakeCommentView;
import make_comment.use_case.MakeCommentFactory;
import make_comment.use_case.MakeCommentDsGateway;
import make_comment.use_case.MakeCommentInteractor;
import make_comment.use_case.MakeCommentOutputBoundary;
import view_comment.drivers.ViewCommentDatabaseAccess;
import view_comment.interface_adapters.ViewCommentController;
import view_comment.interface_adapters.ViewCommentPresenter;
import view_comment.interface_adapters.ViewCommentViewModel;
import view_comment.ui.ViewCommentView;
import view_comment.use_case.ViewCommentDsGateway;
import view_comment.use_case.ViewCommentInputBoundary;
import view_comment.use_case.ViewCommentInteractor;
import view_comment.use_case.ViewCommentOutputBoundary;
import view_post.drivers.ViewPostDatabaseAccess;
import view_post.interface_adapters.ViewPostController;
import view_post.interface_adapters.ViewPostPresenter;
import view_post.interface_adapters.ViewPostViewModel;
import view_post.ui.PostListView;
import view_post.ui.ViewPostView;
import view_post.use_case.ViewPostDsGateway;
import view_post.use_case.ViewPostInteractor;

import javax.swing.*;

public class ViewPostViewTest {
    public static void main(String[] args) {
        String partialPath = "src/test/java/view_post/";

        // creating makeCommentView
        MakeCommentFactory commentFactory = new MakeCommentFactory();
        MakeCommentViewModel makeCommentViewModel = new MakeCommentViewModel();
        MakeCommentOutputBoundary makeCommentPresenter = new MakeCommentPresenter(makeCommentViewModel);
        MakeCommentDsGateway makeCommentDatabaseAccess = new MakeCommentDatabaseAccess(partialPath);
        MakeCommentInteractor makeCommentInteractor = new MakeCommentInteractor(makeCommentDatabaseAccess, makeCommentPresenter, commentFactory);
        MakeCommentController makeCommentController = new MakeCommentController(makeCommentInteractor);
        MakeCommentView makeCommentView = new MakeCommentView(makeCommentController);

        FavouriteUserReaderInterface userFactory = new FavouriteUserFactory();
        FavouritePostReaderInterface postFactory = new FavouritePostFactory();
        FavouriteViewModel favouriteViewModel = new FavouriteViewModel();
        FavouritePresenter favouritePresenter = new FavouritePresenter(favouriteViewModel);
        FavouriteDatabaseAccess dataAccess = new FavouriteDatabaseAccess(postFactory, userFactory, partialPath);
        FavouriteInteractor favouriteInteractor = new FavouriteInteractor(dataAccess, favouritePresenter);
        FavouriteController favouriteController = new FavouriteController(favouriteInteractor);
        FavouriteView favouriteView = new FavouriteView(favouriteController);

        DeletePostReaderInterface postFactory1 = new DeletePostFactory();
        DeletePostViewModel deletePostViewModel = new DeletePostViewModel();
        DeletePostOutputBoundary deletePostPresenter = new DeletePostPresenter(deletePostViewModel);
        DeletePostDsGateway deletePostDataAccess = new DeletePostDataAccess("", postFactory1);
        DeletePostInputBoundary deletePostInteractor = new DeletePostInteractor((DeletePostPresenter) deletePostPresenter, deletePostDataAccess);
        DeletePostController deletePostController = new DeletePostController(deletePostInteractor);
        DeleteView deleteView = new DeleteView(deletePostController);

        ViewCommentViewModel viewCommentViewModel = new ViewCommentViewModel();
        ViewCommentDsGateway viewCommentDatabaseAccess = new ViewCommentDatabaseAccess("");
        ViewCommentOutputBoundary viewCommentPresenter = new ViewCommentPresenter(viewCommentViewModel);
        ViewCommentInputBoundary viewCommentInteractor = new ViewCommentInteractor(viewCommentDatabaseAccess, viewCommentPresenter);
        ViewCommentController viewCommentController = new ViewCommentController(viewCommentInteractor);
        ViewCommentView viewCommentView = new ViewCommentView(viewCommentController);

        FilterPostViewModel filterPostViewModel = new FilterPostViewModel();
        FilterPostOutputBoundary filterPostPresenter = new FilterPostPresenter(filterPostViewModel);
        FilterPostDsGateway filterPostDataAccess = new FilterPostDataAccess(partialPath);
        FilterPostInputBoundary filterPostInteractor = new FilterPostInteractor(filterPostDataAccess, filterPostPresenter);
        FilterPostController filterPostController = new FilterPostController(filterPostInteractor);
        ViewPostViewModel viewPostViewModel = new ViewPostViewModel();
        ViewPostPresenter viewPostPresenter = new ViewPostPresenter(viewPostViewModel);
        ViewPostDsGateway viewPostGateway = new ViewPostDatabaseAccess(partialPath);
        ViewPostInteractor viewPostInteractor = new ViewPostInteractor(viewPostGateway, viewPostPresenter);
        ViewPostController viewPostController = new ViewPostController(viewPostInteractor);
        // Requires array of preset tags
        FilterPostBarView filterPostBarView = new FilterPostBarView(new String[]{"sports", "quantum", "math"}, filterPostController);
        PostListView postListView = new PostListView(viewPostController, filterPostBarView);

        ViewPostView viewPostView = new ViewPostView(favouriteView, makeCommentView, deleteView, viewCommentView, postListView);
        viewPostViewModel.addObserver(viewPostView);
        JFrame testFrame = new JFrame("Test");
        testFrame.add(viewPostView);
        viewPostController.viewPost(1);
        testFrame.setVisible(true);
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
