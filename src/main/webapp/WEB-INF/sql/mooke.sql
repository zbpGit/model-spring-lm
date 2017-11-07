#sql("SelectMooke")
  select * from mooke where nickname = ? and type = ? ORDER BY subscript
#end

#sql("SelectIndex")
  select * from mooke where type = ? and subscript = ? and nickname = ?
#end