package exercise.android.reemh.todo_items;

import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Timer;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TodoItemsHolderImplTest{
  @Test
  public void when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");

    // verify
    assertEquals(1, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void when_addingTodoItem_then_checkItsInProgress(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("do shopping");

    // verify
    assertFalse(holderUnderTest.getCurrentItems().get(0).isDone());
  }

  @Test
  public void when_markingDone_then_checkItsDone(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("do shopping");
    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(0));
    // verify
    assertTrue(holderUnderTest.getCurrentItems().get(0).isDone());
  }

  @Test
  public void when_deleteItem_then_checkItsdeleted(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("do shopping");
    holderUnderTest.deleteItem(holderUnderTest.getCurrentItems().get(0));
    // verify
    assertEquals(0, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void when_addingMultipleItems_then_checkSizeCorrect(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    for(int i = 1; i <= 5; i++)
    {
      holderUnderTest.addNewInProgressItem("item" + i);
    }
    assertEquals(5, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void when_addingItem_then_checkDescriptionIsCorrect(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item to check");
    assertEquals("item to check", holderUnderTest.getCurrentItems().get(0).getDescription());
  }

  @Test
  public void when_addingNewItem_then_checkItemAtTheTop(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item1");
    holderUnderTest.addNewInProgressItem("item2");

    assertEquals("item2", holderUnderTest.getCurrentItems().get(0).getDescription());
  }
  @Test
  public void when_markDoneItem_then_checkItemMoveDown(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item1");
    holderUnderTest.addNewInProgressItem("item2");
    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(0));

    assertEquals("item2", holderUnderTest.getCurrentItems().get(1).getDescription());
  }

  @Test
  public void when_markDoneItemAndThenMarkInProgress_then_checkItemIsInProgress(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item1");
    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(0));
    holderUnderTest.markItemInProgress(holderUnderTest.getCurrentItems().get(0));

    assertFalse(holderUnderTest.getCurrentItems().get(0).isDone());
  }

  @Test
  public void checkSortingComplex() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item3");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item2");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item1");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item0");
    TimeUnit.SECONDS.sleep(1);

    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(2));
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(1));
    TimeUnit.SECONDS.sleep(1);

    assertEquals("item0", holderUnderTest.getCurrentItems().get(0).getDescription());
    assertEquals("item3", holderUnderTest.getCurrentItems().get(1).getDescription());
    assertEquals("item1", holderUnderTest.getCurrentItems().get(2).getDescription());
    assertEquals("item2", holderUnderTest.getCurrentItems().get(3).getDescription());
  }

  @Test
  public void checkSortingComplex2() throws InterruptedException {
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    holderUnderTest.addNewInProgressItem("item3");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item2");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item1");
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.addNewInProgressItem("item0");
    TimeUnit.SECONDS.sleep(1);

    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(2));
    TimeUnit.SECONDS.sleep(1);
    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(1));
    TimeUnit.SECONDS.sleep(1);

    holderUnderTest.deleteItem(holderUnderTest.getCurrentItems().get(2));
    holderUnderTest.deleteItem(holderUnderTest.getCurrentItems().get(0));


    assertEquals("item3", holderUnderTest.getCurrentItems().get(0).getDescription());
    assertEquals("item2", holderUnderTest.getCurrentItems().get(1).getDescription());
    assertEquals(2, holderUnderTest.getCurrentItems().size());
  }
}