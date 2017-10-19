#sql("SelectCount")
  SELECT count(*) as ct from official WHERE nickname = ? and  look = '0'
#end

#sql("SelectAll")
  SELECT  * from official WHERE nickname = ? and identification = '0' order by `timestamp` DESC
#end