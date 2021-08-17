package com.seyeong.databaseexample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues as ContentValues

class SqliteHelper(context : Context, name : String, version:Int) :
    SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "CREATE TABLE memo (" +
                "no integer primary key, " +
                "content text, " +
                "datetime integer" +
                ")"

        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertMemo(memo: Memo) { // Memo 삽입 메서드
        val values = ContentValues() // ContentValues 클래스 정의
        values.put("content", memo.content)
        values.put("datetime", memo.datetime) // 값 삽입

        val wd = writableDatabase // 쓰기 모드
        wd.insert("memo", null, values) // 테이블명과 함께 앞에서 작성한 값을 넘겨준다.
        wd.close() // 사용한 뒤에 닫아준다.
    }

    fun selectMemo() : MutableList<Memo> { // 조회 메서드는 반환값이 있으므로 리턴 타입을 정의해준다.
        val list = mutableListOf<Memo>() // 반환할 값을 선언. 해당 변수를 반환한다.

        val select = "SELECT * FROM memo" // 조회할 쿼리를 변수에 담아둔다.

        val rd = readableDatabase // 읽기 모드
        val cursor = rd.rawQuery(select, null) // rawQuery 메서드에 앞에 작성한 쿼리를 담아 커서 형태로 반환한다.

        while( cursor.moveToNext()) { // moveToNext : 다음 줄에 사용할 수 있는 레코드가 있는지 여부를 반환
            // 반복문을 돌며 테이블에 정의된 3개 칼럼에서 값을 꺼내 변수에 담는다.
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            list.add(Memo(no, content, datetime)) // Memo 클래스 형태로 값을 저장하여 반환할 목록에 추가한다.
        }
        cursor.close() // 커서 닫음
        rd.close() // 읽기 모드 닫음

        return list
    }

    fun updateMemo(memo: Memo) { // 수정 메서드
        val values = ContentValues() // ContentValues 클래스 생성
        values.put("content", memo.content)
        values.put("datetime", memo.datetime) // 값 삽입

        val wd = writableDatabase // 읽기 모드
        wd.update("memo", values, "no = ${memo.no}", null)
        // wd.update(테이블명, 수정할 값, 수정할 조건, null) 순서로 입력한다. 이 때, 수정할 조건은 PRIMARY KEY로 지정된 칼럼을 사용한다.
        // 또한 여기서는 세번째 파라미터에 no = ${memo.no}로 모두 입력해주었지만 이렇게 하지 않고
        // 세번째 파라미터에 no = ? 를 넣고 네번째 파라미터에 ArrayOf("{memo.no}") 형태로 넣어도 가능하다.
        wd.close()
    }

    fun deleteMemo(memo: Memo) { // 삭제 메서드
        val delete = "DELETE FROM memo WHERE no = ${memo.no}"

        val db = writableDatabase // 쓰기 모드
        db.execSQL(delete) // 삭제 쿼리 실행
        db.close() // 닫기
    }

}

data class Memo(var no:Long?, var content: String, var datetime: Long)