package todo.domain.service.todo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.terasoluna.gfw.common.exception.BusinessException;

import todo.domain.model.Todo;
import todo.domain.repository.todo.TodoRepository;

/**
 * Service Test
 * モッククラスによるRepositoryメソッドのモック化
 * 
 */
public class TodoServiceImplTestVerMockClass {

	
	TodoServiceImpl target;
	
	@Before
	public void setUp() throws Exception{
		//テストメソッドでモックを切り替えるため処理なし
	}
	
	//正常に動作したパターン
	@Test
	public void testFinishOK() throws Exception{
		
		//モッククラスの適用（TestFinishOKMock）
		target = new TodoServiceImpl();
		TodoRepository mockTodoRepository = new TestFinishOKMock();
		target.todoRepository = mockTodoRepository;
		
		//引数設定
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		
		//finishメソッドのテスト
		Todo todo = target.finish(todoId);
		
		//結果検証（assertTodoメソッドはメソッドの実行によって返ってきたTodoオブジェクトを検証するprivateメソッド）
		assertTodo(todo);
		
	}
	
	//取得したTodoオブジェクトのfinishedが既にtrueで異常が発生したパターン
	//@Testのexpectedには期待するExceptionクラスを設定する。こうすることでExceptionが発生することは想定通りということを宣言している。
	@Test(expected = BusinessException.class)
	public void testFinishNG() throws Exception{
		
		//モッククラスの適用（TestFinishNGMock）
		target = new TodoServiceImpl();
		TodoRepository mockTodoRepository = new TestFinishNGMock();
		target.todoRepository = mockTodoRepository;
		
		//引数設定
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		
		//try-catch文はなくてもJunitとしては正常になるが、printStackTraceメソッドでエラーの内容を表示させている。
		try {
			target.finish(todoId);
		}catch (BusinessException e) {
			// TODO: handle exception
			e.printStackTrace();
			
			throw e;
		}
	}

	//データ検証用メソッド
	//期待値はTestFinishOKMock()にて作成したデータのfinishedをtrueに変更したもの
	//検証対象はtarget.finish(todoId)から取得したデータ
	public void assertTodo(Todo todo) throws Exception{
		
		Todo expectTodo = new TestFinishOKMock().findOne("");
		expectTodo.setFinished(true);
		
		assertEquals(expectTodo.getTodoId(), todo.getTodoId());
		assertEquals(expectTodo.getTodoTitle(), todo.getTodoTitle());
		assertEquals(expectTodo.isFinished(), todo.isFinished());
		assertEquals(expectTodo.getCreatedAt(), todo.getCreatedAt());
		
		}
}
