#coding=utf-8
from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:asdf6869@localhost:3306/pmlfinalproject?charset=utf8'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

db = SQLAlchemy(app, use_native_unicode='utf8mb4')


class User(db.Model):
    __tablename__ = 'users'

    userId = db.Column(db.Integer, primary_key = True, nullable = False)
    userName = db.Column(db.String(45), nullable = False)
    account = db.Column(db.String(45), nullable = False, unique = True)
    password = db.Column(db.String(45), nullable = False)
    picUrl = db.Column(db.String(1000), nullable = False)
    phone = db.Column(db.String(16), nullable = False, unique = True)

    user_tickets = db.relationship("Ticket", back_populates="ticket_users") # connection from User to Ticket

    def __repr__(self):
        return '<User %r>' % self.userName

class Cinema(db.Model):
    __tablename__ = 'cinemas'

    cinemaId = db.Column(db.Integer, primary_key = True, nullable = False)
    cinemaName = db.Column(db.String(45), nullable = False, unique = True)
    address = db.Column(db.String(45), nullable = False)
    lowestPrice = db.Column(db.Integer, nullable = False)

    # connection from Cinema to Cinema_hall
    cinema_cinema_halls = db.relationship("Cinema_hall", back_populates="cinema_hall_cinemas")

    # connection from Cinema to Seat
    #cinema_seats = db.relationship("Seat", back_populates="seat_cinemas")

    # connection from Cinema to Movie_session
    cinema_movie_sessions = db.relationship("Movie_session", back_populates="movie_session_cinemas")

    # connection from Cinema to Ticket
    cinema_tickets = db.relationship("Ticket", back_populates="ticket_cinemas")

    def __repr__(self):
        return '<Cinema %r>' % self.cinemaName

class Cinema_hall(db.Model):
    __tablename__ = 'halls'

    hallId = db.Column(db.Integer, primary_key = True, nullable = False)
    hallNum = db.Column(db.Integer, nullable = False)
    seatRowNum = db.Column(db.Integer, nullable = False)
    seatColNum = db.Column(db.Integer, nullable = False)


    ch_cinemaId = db.Column(db.Integer, db.ForeignKey('cinemas.cinemaId'))

    # connection from Cinema_hall to Cinema
    cinema_hall_cinemas = db.relationship("Cinema", back_populates="cinema_cinema_halls", foreign_keys=[ch_cinemaId]) 

    # connection from Cinema_hall to Seat
    #cinema_hall_seats = db.relationship("Seat", back_populates="seat_cinema_halls")

    # connection from Cinema_hall to Movie_session
    cinema_hall_movie_sessions = db.relationship("Movie_session", back_populates="movie_session_cinema_halls")

    # connection from Cinema_hall to Ticket
    cinema_hall_tickets = db.relationship("Ticket", back_populates="ticket_cinema_halls")


    def __repr__(self):
        return '<Cinema_hall %r>' % self.hallId

class Seat(db.Model):
    __tablename__ = 'seats'

    seatId = db.Column(db.Integer, primary_key = True, nullable = False, autoincrement = True)
    seatRow = db.Column(db.Integer, nullable = False)
    seatCol = db.Column(db.Integer, nullable = False)

    s_sessionId = db.Column(db.Integer, db.ForeignKey('sessions.sessionId'), nullable = False)

    # connection from Seat to Cinema
    #seat_cinemas = db.relationship("Cinema", back_populates="cinema_seats", foreign_keys=[s_cinemaId])

    # connection from Seat to Cinema_hall
    #seat_cinema_halls = db.relationship("Cinema_hall", back_populates="cinema_hall_seats", foreign_keys=[s_hallId])

    # connectionfrom Seat to Movie_session
    seat_movie_sessions = db.relationship("Movie_session", back_populates="movie_session_seats")

    # connection from Seat to Ticket
    seat_tickets = db.relationship("Ticket", back_populates="ticket_seats")

    def __repr__(self):
        return '<Seat %r>' % self.seatId

class Movie(db.Model):
    __tablename__ = 'movies'

    movieId = db.Column(db.Integer, primary_key = True, nullable = False)
    movieName = db.Column(db.String(45), nullable = False, unique = True)
    movieType = db.Column(db.String(45), nullable = False)
    director = db.Column(db.String(45), nullable = False)
    starring = db.Column(db.String(45), nullable = False)
    length = db.Column(db.Integer, nullable = False)
    
    englishName = db.Column(db.String(45), nullable = False)
    showDate = db.Column(db.String(45), nullable = False)
    introduction = db.Column(db.String(3000), nullable = False)
    grade = db.Column(db.Float, nullable = False)

    # connection from Movie to Movie_session
    movie_movie_sessions = db.relationship("Movie_session", back_populates="movie_session_movies")

    # connection from Movie to Ticket
    movie_tickets = db.relationship("Ticket", back_populates="ticket_movies")

    def __repr__(self):
        return '<Movie %r>' % self.movieName

class Movie_session(db.Model):
    __tablename__ = 'sessions'

    sessionId = db.Column(db.Integer, primary_key = True, nullable = False)
    date = db.Column(db.Integer, nullable = False)
    startTime = db.Column(db.Integer, nullable = False)
    price = db.Column(db.Float, nullable = False)

    ms_cinemaId = db.Column(db.Integer, db.ForeignKey('cinemas.cinemaId'))
    ms_hallId = db.Column(db.Integer, db.ForeignKey('halls.hallId'))
    ms_movieId = db.Column(db.Integer, db.ForeignKey('movies.movieId'))

    # connection from Movie_session to Cinema
    movie_session_cinemas = db.relationship("Cinema", back_populates="cinema_movie_sessions", foreign_keys=[ms_cinemaId])

    # connection from Movie_session to Cinema_hall
    movie_session_cinema_halls = db.relationship("Cinema_hall", back_populates="cinema_hall_movie_sessions", foreign_keys=[ms_hallId])

    # connection from Movie_session to Movie
    movie_session_movies = db.relationship("Movie", back_populates="movie_movie_sessions", foreign_keys=[ms_movieId])

    # connection from Movie_session to Seat
    movie_session_seats = db.relationship("Seat", back_populates="seat_movie_sessions")

    # connection from Movie_session to Ticket
    movie_session_tickets = db.relationship("Ticket", back_populates="ticket_movie_sessions")

    def __repr__(self):
        return '<Movie_session %r>' % self.sessionId

class Ticket(db.Model):
    __tablename__ = 'tickets'

    ticketId = db.Column(db.Integer, primary_key = True, nullable = False)
    isPrinted = db.Column(db.Boolean, nullable = False, default = False)

    tk_userId = db.Column(db.Integer, db.ForeignKey('users.userId'))
    tk_cinemaId = db.Column(db.Integer, db.ForeignKey('cinemas.cinemaId'))
    tk_movieId = db.Column(db.Integer, db.ForeignKey('movies.movieId'))
    tk_sessionId = db.Column(db.Integer, db.ForeignKey('sessions.sessionId'))
    tk_hallId = db.Column(db.Integer, db.ForeignKey('halls.hallId'))

    tk_seat = db.Column(db.Integer, db.ForeignKey('seats.seatId'))

    # connection from Ticket to User
    ticket_users = db.relationship("User", back_populates="user_tickets", foreign_keys=[tk_userId])

    # connection from Ticket to Movie
    ticket_movies = db.relationship("Movie", back_populates="movie_tickets", foreign_keys=[tk_movieId])

    # connection from Ticket to Cinema
    ticket_cinemas = db.relationship("Cinema", back_populates="cinema_tickets", foreign_keys=[tk_cinemaId])

    # connection from Ticket to Movie_session
    ticket_movie_sessions = db.relationship("Movie_session", back_populates="movie_session_tickets", foreign_keys=[tk_sessionId])

    # connection from Ticket to Cinema_hall
    ticket_cinema_halls = db.relationship("Cinema_hall", back_populates="cinema_hall_tickets", foreign_keys=[tk_hallId])

    # connection from Ticket to Seat
    ticket_seats = db.relationship("Seat", back_populates="seat_tickets", foreign_keys=[tk_seat])

    def __repr__(self):
        return '<Tickets %r>' % self.ticketId

if __name__ == '__main__':
    db.drop_all()
    db.create_all()
    user = User()
    user.userName = 'cnan'
    user.password = 'asdf1234'
    user.phone = '13719341638'
    user.account = 'a12345'
    user.picUrl = 'abcdefg'
    db.session.add(user)
    db.session.commit()

    cinema = Cinema()
    cinema.cinemaName = '金逸珠江国际影城广州大学城店'
    cinema.address = '番禺区小谷街道贝岗村中二横路1号GOGO新天地商业广场二期二楼'
    cinema.lowestPrice = 34
    db.session.add(cinema)
    db.session.commit()

    cinema = Cinema()
    cinema.cinemaName = '广东科学中心巨幕影院'
    cinema.address = '番禺区广州大学城西六路168号'
    cinema.lowestPrice = 29.9
    db.session.add(cinema)
    db.session.commit()

    cinema = Cinema()
    cinema.cinemaName = '广州万达影城万胜广场店'
    cinema.address = '海珠区新港东路1236号万胜广场五层'
    cinema.lowestPrice = 24.9
    db.session.add(cinema)
    db.session.commit()

    cinema = Cinema()
    cinema.cinemaName = '星河影城番禺南村店'
    cinema.address = '番禺区番禺南村兴业大道之一人人佳购物广场2楼'
    cinema.lowestPrice = 32.9
    db.session.add(cinema)
    db.session.commit()

    cinema = Cinema()
    cinema.cinemaName = '广州hmv映联万和国际影城(南丰汇店)'
    cinema.address = '海珠区新港东路618号南丰汇广场3楼'
    cinema.lowestPrice = 29.9
    db.session.add(cinema)
    db.session.commit()
    #金逸1号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 1
    cinema_hall.seatRowNum = 10
    cinema_hall.seatColNum = 15
    cinema_hall.ch_cinemaId = 1
    db.session.add(cinema_hall)
    db.session.commit()
    #金逸2号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 2
    cinema_hall.seatRowNum = 8
    cinema_hall.seatColNum = 8
    cinema_hall.ch_cinemaId = 1
    db.session.add(cinema_hall)
    db.session.commit()
    #金逸3号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 3
    cinema_hall.seatRowNum = 8
    cinema_hall.seatColNum = 8
    cinema_hall.ch_cinemaId = 1
    db.session.add(cinema_hall)
    db.session.commit()
    #科中1号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 1
    cinema_hall.seatRowNum = 10
    cinema_hall.seatColNum = 10
    cinema_hall.ch_cinemaId = 2
    db.session.add(cinema_hall)
    db.session.commit()
    #万达1号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 1
    cinema_hall.seatRowNum = 10
    cinema_hall.seatColNum = 10
    cinema_hall.ch_cinemaId = 3
    db.session.add(cinema_hall)
    db.session.commit()
    #星河1号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 1
    cinema_hall.seatRowNum = 10
    cinema_hall.seatColNum = 10
    cinema_hall.ch_cinemaId = 4
    db.session.add(cinema_hall)
    db.session.commit()
    #万和1号厅
    cinema_hall = Cinema_hall()
    cinema_hall.hallNum = 1
    cinema_hall.seatRowNum = 10
    cinema_hall.seatColNum = 10
    cinema_hall.ch_cinemaId = 5
    db.session.add(cinema_hall)
    db.session.commit()
    #8 头号玩家
    movie = Movie()
    movie.movieName = '头号玩家'
    movie.movieType = '科幻,动作,冒险'
    movie.director = '史蒂文·斯皮尔伯格'
    movie.starring = '泰伊·谢里丹,奥利维亚·库克,西蒙·佩吉'
    movie.length = 140
    movie.englishName = 'Number one player' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20180330'#db.Column(db.String(45), nullable = False)
    movie.introduction = '该片根据恩斯特·克莱恩同名小说改编，讲述了一个现实生活中无所寄托、沉迷游戏的大男孩，凭着对虚拟游戏设计者的深入剖析，历经磨难，找到隐藏在关卡里的三把钥匙，成功通关游戏，并且还收获了网恋女友的故事'
    movie.grade = 7.5 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #1电影复仇者联盟
    movie = Movie()
    movie.movieName = '复仇者联盟'
    movie.movieType = '科幻'
    movie.director = '乔斯·韦登'
    movie.starring = '小罗伯特·唐尼, 克里斯·埃文斯, 马克·鲁法'
    movie.length = 120
    movie.englishName = 'Revengers League' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20120511'#db.Column(db.String(45), nullable = False)
    movie.introduction = '一股突如其来的强大邪恶势力对地球造成致命威胁，没有任何一个超级英雄能够单独抵挡。长期致力于保护全球安危的神盾局感到措手不及，其指挥官“独眼侠”尼克·弗瑞（塞缪尔·杰克逊 饰）意识到他必须创建一个“史上最强”的联盟组织，云集各方超级英雄一起发威，才能拯救世界于水深火热，抵御黑暗势力的侵袭。于是由六大超级英雄——钢铁侠（小罗伯特·唐尼 饰）、美国队长（克里斯·埃文斯 饰）、雷神（克里斯·海姆斯沃斯 饰）、绿巨人（马克·鲁弗洛 饰）、黑寡妇（斯嘉丽·约翰逊 饰）和鹰眼侠（杰瑞米·雷纳 饰）组成的 “复仇者联盟”应运而生。他们各显神通，团结一心，终于战胜了邪恶势力，保证了地球的安全。'#db.Column(db.String(3000), nullable = False)
    movie.grade = 8.8 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #2电影死侍
    movie = Movie()
    movie.movieName = '死侍'
    movie.movieType = '动作'
    movie.director = '大卫·雷奇'
    movie.starring = '瑞安·雷诺兹, 莫瑞娜·巴卡琳, T·J·米勒, 乔什·布洛林, 忽那汐里'
    movie.length = 119
    movie.englishName = 'Dead pool' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20120518'#db.Column(db.String(45), nullable = False)
    movie.introduction = '《死侍2》的故事发生在第一部结局的大概两年之后 ，韦德·威尔森和他的女友瓦内莎幸福地生活在一起，平时他也以死侍的身份打击犯罪。 在经历了一次个人悲剧后，他打算结束自己的生命，却被钢力士和少年弹头收留了，成为了一名受训中的X战警。韦德很快与拉塞尔（朱利安·迪尼森饰演）相识了，后者是一个年轻的变种人，曾经受到过监护人的虐待。 死侍想要帮助拉塞尔，但是两个人却进了牢房，在那里他们遇到了电索。电索具有穿越时间的能力，他穿越到当下来刺杀拉塞尔，阻止对方在日后走上邪路，摧毁电索的人生。 为了保护拉塞尔，死侍创建了X势力小组，招揽了一些超级英雄，包括多米诺、喧嚣（泰瑞·克鲁斯饰演）、碎星（刘易斯·谭饰演）、札格斯特（比尔·斯卡斯加德饰演），和拥有隐身能力却从来不说话的消隐者——这个角色的署名是布拉德·皮特。另外，团队里还有一个没有超能力的成员彼得（Rob Delaney饰演）。 惊恐万分的拉塞尔在狱中和X战警的一个死对头结交为好友，而他距离投向黑暗面只有一步之遥了。 死侍劝服电索让他和拉塞尔谈谈，来阻止未来悲剧的发生，也能让电索放过那个孩子一命。但是这个任务，需要X战警和X势力一同合作，来阻止拉塞尔新朋友的阴谋。'#db.Column(db.String(3000), nullable = False)
    movie.grade = 8.8 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #4电影爵迹
    movie = Movie()
    movie.movieName = '爵迹'
    movie.movieType = '玄幻'
    movie.director = '郭敬明'
    movie.starring = '范冰冰, 吴亦凡, 陈学冬, 陈伟霆'
    movie.length = 121
    movie.englishName = 'Jue ji' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20160929'#db.Column(db.String(45), nullable = False)
    movie.introduction = '传说中的神话奥汀大陆分为水、风、火、地四个国家，每个国家都有精通魂术的人，其中最厉害的七个被称为王爵。水国普通男孩麒零（陈学冬 饰）离奇地被七度王爵银尘（吴亦凡饰）收为使徒，卷入了这场魂术的风暴，水国隐藏的秘密也渐渐浮出水面。 在奥汀大陆这个神秘的世界里，西之亚斯兰帝国七度王爵银尘寻找到自己的使徒麒零，却无意中发现了上代一度王爵吉尔伽美什可能还活着的秘密，于是义无反顾的投入到追寻之中，而与此同时作为侵蚀者的王爵幽冥（陈伟霆 饰）、特蕾娅（郭采洁 饰）也收到了对银尘和鬼山莲泉（范冰冰 饰）等人的杀戮红讯。一场王爵和使徒们为了真相和荣誉的战役一触即发。'#db.Column(db.String(3000), nullable = False)
    movie.grade = 2.8 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #5电影金银岛
    movie = Movie()
    movie.movieName = '金银岛'
    movie.movieType = '冒险'
    movie.director = '斯蒂夫·巴伦'
    movie.starring = '艾迪·伊扎德, 伊利亚·伍德'
    movie.length = 109
    movie.englishName = 'Treasure Island' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20120505'#db.Column(db.String(45), nullable = False)
    movie.introduction = '吉姆·哈弗金斯与母亲在乡下经营一家小旅。某天，旅馆接收一名惶恐嗜酒的海盗水手比利，他曾是臭名昭著的海盗头子福林特船长的手下。福林特凶残贪婪，手中握有一份藏宝图的地图。在他死之后，最为忠诚的比利似乎掌握了地图，而他也成为所有海盗急于找到的目标。吉姆从死去的比利身上找到藏宝图，借着和当地的伙伴乘船前往那个堆满了无数宝贝的金银岛。与此同时，邪恶的海盗乔装打扮登上他们的船，随时准备攫取令人垂涎欲滴的宝藏'#db.Column(db.String(3000), nullable = False)
    movie.grade = 8.8 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #6电影超人总动员
    movie = Movie()
    movie.movieName = '超人总动员'
    movie.movieType = '动画,动作,冒险'
    movie.director = '布拉德·伯德'
    movie.starring = '格雷格·T·尼尔森,霍利·亨特'
    movie.length = 115
    movie.englishName = 'Fantastic Four' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20041105'#db.Column(db.String(45), nullable = False)
    movie.introduction = '鲍勃是一个超人特工，他惩恶扬善，深受街坊邻里的爱戴。“不可思议”先生就是他的光荣外号。他和另一个超人特工“弹力女超人”相爱，两人结婚后过上平静的生活。15年过去了，鲍勃已经像普通人一样生活，当上了保险公司的理赔员。然而他心中还是有着技痒之时。当他知道有发明家要展开攻击超人特工队、毁灭人类的计划，鲍勃终于按捺不住了。他要重出江湖，挽救人类护卫地球。但中年的超人已经大腹便便，他和敌人的斗争充满了悬念。鲍勃的妻子也跟丈夫一起投入到这场艰巨的战争中。'
    movie.grade = 8.9 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #7侏罗纪世界2
    movie = Movie()
    movie.movieName = '侏罗纪世界2'
    movie.movieType = '科幻,动作,冒险'
    movie.director = '胡安·安东尼奥·巴亚纳'
    movie.starring = '克里斯·帕拉特,布莱丝·达拉斯·霍华德'
    movie.length = 128
    movie.englishName = 'Jurassic world 2' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20180615'#db.Column(db.String(45), nullable = False)
    movie.introduction = '侏罗纪世界主题公园及豪华度假村被失控的恐龙们摧毁已有三年。如今，纳布拉尔岛已经被人类遗弃，岛上幸存的恐龙们在丛林中自给自足。 当岛上的休眠火山开始活跃以后，欧文与克莱尔发起了一场运动，想要保护岛上幸存的恐龙们免于灭绝。 欧文一心想要找到自己依然失踪在野外的迅猛龙首领布鲁，克莱尔如今也尊重起这些生物，以保护它们为己任。两人在熔岩开始喷发时来到了危险的小岛，他们的冒险也揭开了一个可能让地球回到史前时代般混乱秩序的阴谋。'
    movie.grade = 7.8 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()
    #3电影深海越狱
    movie = Movie()
    movie.movieName = '深海越狱'
    movie.movieType = '动作'
    movie.director = '帕夏·帕特里基'
    movie.starring = '尚格·云顿, 杜夫·龙格尔, 阿尔·萨皮恩扎'
    movie.length = 104
    movie.englishName = 'Deep sea prison break' #db.Column(db.String(45), nullable = False)
    movie.showDate = '20180608'#db.Column(db.String(45), nullable = False)
    movie.introduction = '顶级特工惠勒（尚格·云顿饰）莫名陷入一场战局，被捕后困于深海的核潜艇中，而这座核潜艇则是美国CIA的一处黑狱。在黑狱中，惠勒遇上了同被困在此处的特工马可（杜夫·龙格尔饰）。潜艇在大海深处巡游，而潜艇内部又有着复杂精密的机关设计，再加上四处潜伏的未知敌人，想要逃出去根本是无稽之谈。再者，就算能逃出去，茫茫深海，他又该何去何从？'#db.Column(db.String(3000), nullable = False)
    movie.grade = 8.6 #db.Column(db.Float, nullable = False)
    db.session.add(movie)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180630
    movie_session.startTime = 1200
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180630
    movie_session.startTime = 1528
    movie_session.price = 40.6
    movie_session.ms_hallId = 3
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180630
    movie_session.startTime = 1300
    movie_session.price = 40.6
    movie_session.ms_hallId = 2
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180701
    movie_session.startTime = 1300
    movie_session.price = 40.6
    movie_session.ms_hallId = 2
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180701
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180701
    movie_session.startTime = 2300
    movie_session.price = 40.6
    movie_session.ms_hallId = 3
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1300
    movie_session.price = 40.6
    movie_session.ms_hallId = 3
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1600
    movie_session.price = 40.6
    movie_session.ms_hallId = 2
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180701
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 1
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 1
    movie_session.ms_cinemaId = 2
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 2200
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 3
    movie_session.ms_cinemaId = 2
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 1
    movie_session.ms_cinemaId = 3
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 2200
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 2
    movie_session.ms_cinemaId = 3
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 1
    movie_session.ms_cinemaId = 4
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 2200
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 4
    movie_session.ms_cinemaId = 4
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1900
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 1
    movie_session.ms_cinemaId = 5
    db.session.add(movie_session)
    db.session.commit()

    movie_session = Movie_session()
    movie_session.date = 20180702
    movie_session.startTime = 1600
    movie_session.price = 40.6
    movie_session.ms_hallId = 1
    movie_session.ms_movieId = 3
    movie_session.ms_cinemaId = 5
    db.session.add(movie_session)
    db.session.commit()


#last change:20180629 21:48

