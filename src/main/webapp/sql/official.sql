#sql("SelectCount")
  SELECT count(*) as ct from official WHERE nickname = ? and  look = '0'
#end