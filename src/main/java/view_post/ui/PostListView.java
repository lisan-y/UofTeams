package view_post.ui;

import filter_post.ui.FilterPostBarView;
import view_post.interface_adapters.ViewPostController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The view that displays the search engine and the list of posts the user can choose to view.
 */
public class PostListView extends JPanel implements PropertyChangeListener, ListSelectionListener {
    // JPanel that includes the scrollable list of posts a user can click to view
    private final JPanel postList;
    // the list of titles being displayed in the scrollable list
    private String[] titles;
    // the list of ids of the posts represented by the titles
    private int[] ids;
    // controller of this use case
    private final ViewPostController controller;
    //
    private JList<String> list;
    private int deletedPostID;

    /**
     * Initializes PostListView.
     * @param controller A ViewPostController object that will be called when a post is selected to view
     */
    public PostListView(ViewPostController controller, FilterPostBarView filterBar){
        this.setPreferredSize(new Dimension(400, 680));
        // initializing instance variables
        this.postList = new JPanel();
        this.add(this.postList);
        // the FilterPostBarView is added directly into PostListView so that it does not get removed in displayList()
        this.add(filterBar);
        this.titles = null;
        this.ids = null;
        this.controller = controller;
        this.list = null;

        // Default view when there are no posts to show
        JLabel noPostsText = new JLabel("Press Search :)");
        this.postList.add(noPostsText);
    }

    /**
     * Updates the scrollable list of posts. When there are no posts to display, the default view is presented.
     * (Disclaimer : this method depends on the indices of titles and ids to match exactly)
     * @param titles A String array of the titles of the posts being displayed on the scrollable list
     * @param ids An integer array of the IDs of the posts being displayed on the scrollable list
     */
    private void displayList(String[] titles, int[] ids){
        this.titles = titles;
        this.ids = ids;
        this.postList.removeAll();

        if (titles.length == 0){
            this.defaultDisplay(); // if there are no posts display the defaultDisplay
        }else{
            JList<String> titleList = new JList<>(this.titles);
            this.list = titleList;
            this.list.addListSelectionListener(this);
            titleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            titleList.setLayoutOrientation(JList.VERTICAL);
            titleList.setVisibleRowCount(-1);
            JScrollPane scrollableList = new JScrollPane(this.list);
            scrollableList.setPreferredSize(new Dimension(400, 400));
            postList.add(scrollableList);
            postList.setBounds(0, 180, 400, 400);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    /**
     * Displays the message when there are no posts to show on the scrollable list of posts
     */
    private void defaultDisplay(){
        JLabel noPostsText = new JLabel("No posts to show :( Please search!");
        this.postList.removeAll();
        this.postList.add(noPostsText);
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * When a property change is fired in the FilterPost Use Case, PostListView responds by updating its list of posts
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    @SuppressWarnings("all")
    public void propertyChange(PropertyChangeEvent evt) {
        if ("Search".equals(evt.getPropertyName())){
            ArrayList<Object> newData = (ArrayList<Object>) evt.getNewValue();
            String[] titles = (String[]) newData.get(0);
            int[] ids = (int[]) newData.get(1);
            if (ids.length == 0){
                defaultDisplay();
            } else {
                this.displayList(titles, ids);
            }
        }
        //if a post is deleted, then the PostListView needs to be refreshed.
        if ("success".equals(evt.getPropertyName())){
            this.refresh();
        }
        if("Log Out".equals(evt.getPropertyName())){
            this.defaultDisplay();
        }
    }

    /**
     * When an item is selected in the scrollable list, the ViewPost use case is triggered and displays the
     * post in the larger panel
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int index = this.list.getSelectedIndex();
        int postId = this.ids[index];
        controller.viewPost(postId);
    }

    /**
     * Update the PostListView when a post has been deleted.
     * */
    public void refresh(){
        //this.ids are the ids currently in the PostListView; this.titles are analogous.
        ArrayList<Integer> tempIDs = new ArrayList<>();
        for (int id : ids) {
            tempIDs.add(id);
        }
        ArrayList<String> tempTitles = new ArrayList<>();
        Collections.addAll(tempTitles, titles);

        int indexToBeDeleted = tempIDs.indexOf(this.deletedPostID);
        tempIDs.remove(indexToBeDeleted);
        tempTitles.remove(indexToBeDeleted);
        this.ids = new int[tempIDs.size()];
        for(int i = 0; i < ids.length; i++){
            this.ids[i] = tempIDs.get(i);
        }
        this.titles = new String[tempTitles.size()];
        for(int i = 0; i < titles.length; i++){
            this.titles[i] = tempTitles.get(i);
        }
        this.displayList(this.titles, this.ids);
    }
    public void setDeletedPostID(int id){
        this.deletedPostID = id;
    }
}
