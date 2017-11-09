#sql("SelectP")
  select * from workpicture where wid = ?
#end

#sql("DeleteWid")
  DELETE from workpicture WHERE wid = ? and type = ?
#end