from flask import Flask, jsonify, make_response, abort, request
from flask_sqlalchemy import SQLAlchemy
from model import User, Cinema, Cinema_hall, Seat, Movie, Movie_session, Ticket
import random
import datetime

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:asdf6869@localhost:3306/pmlfinalproject?autocommit=true,charset=utf8mb4'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

db = SQLAlchemy(app)


#用户类

#1通过账号获取用户信息
@app.route('/resource/users/account/<account>', methods=['GET'])
def get_user_account(account):
    user = User.query.filter(User.account == account).first()
    if user:
        return jsonify({'account': user.account, 'userName': user.userName, 'phone': user.phone, 'picUrl': user.picUrl}), 200
    else:
        abort(404)


#2通过手机号码获取用户信息
@app.route('/resource/users/phone/<phone>', methods=['GET'])
def get_user_phone(phone):
    user = User.query.filter(User.phone == phone).first()
    if user:
        return jsonify({'account': user.account, 'userName': user.userName, 'phone': user.phone, 'picUrl': user.picUrl}), 200
    else:
        abort(404)


#3用户注册
@app.route('/resource/users', methods=['POST'])
def create_user():
    if not request.json:
        abort(404)
    user = User.query.filter(User.account == request.json['account']).first()
    if user:
        abort(405)
    user = User.query.filter(User.phone == request.json['phone']).first()
    if user:
        abort(403)
    user = User()
    user.account = request.json['account']
    user.userName = request.json['userName']
    user.password = request.json['password']
    user.phone = request.json['phone']
    user.picUrl = request.json['picUrl']
    db.session.add(user)
    db.session.commit()
    return jsonify({'code': 200}), 200


#4用户登录
@app.route('/resource/users/login', methods=['POST'])
def login():
    if not request.json:
        abort(404)
    user = User.query.filter(User.account == request.json['account']).first()
    if not user:
        user = User.query.filter(User.phone == request.json['phone']).first()
    if not user:
        abort(406)
    if user.password == request.json['password']:
        return jsonify({'userName': user.userName, 'picUrl': user.picUrl, 'account': user.account, 'phone':user.phone}), 200
    else:
        abort(401)


#影院类

#1获取影院信息
@app.route('/resource/cinema/<cinemaName>', methods=['GET'])
def get_cinema(cinemaName):
    cinema = Cinema.query.filter(Cinema.cinemaName == cinemaName).first()
    if not cinema:
        abort(404)
    return jsonify({'cinemaName': cinema.cinemaName, 'address': cinema.address, 'lowestPrice': cinema.lowestPrice}), 200


#2获取所有影院信息
@app.route('/resource/cinema', methods=['GET'])
def get_cinema_all():
    cinemas = Cinema.query.all()
    if len(cinemas) == 0:
        abort(404)
    cinema_list = []
    for index in range(len(cinemas)):
        temp = {
            'cinemaName': cinemas[index].cinemaName,
            'address': cinemas[index].address,
            'lowestPrice': cinemas[index].lowestPrice
        }
        cinema_list.append(temp)
    return jsonify(cinema_list), 200



#3获取影厅信息
@app.route('/resource/cinema/<cinemaName>/hall/<int:hallNum>', methods=['GET'])
def get_hall(cinemaName, hallNum):
    cinema = Cinema.query.filter(Cinema.cinemaName == cinemaName).first()
    if not cinema:
        abort(404)
    cinema_hall = Cinema_hall.query.filter(Cinema_hall.ch_cinemaId == cinema.cinemaId, Cinema_hall.hallNum == hallNum).first()
    if not cinema_hall:
        abort(404)
    return jsonify({'cinemaName': cinema.cinemaName, 'hallNum': cinema_hall.hallNum, 'seatRowNum': cinema_hall.seatRowNum, 'seatColNum': cinema_hall.seatColNum}), 200


#电影排期类

#1获取某场次电影信息
@app.route('/resource/cinema/<cinemaName>/hall/<int:hallNum>/<int:date>/<int:startTime>', methods=['GET'])
def get_movie_session(cinemaName, hallNum, date, startTime):
    cinema = Cinema.query.filter(Cinema.cinemaName == cinemaName).first()
    cinema_hall = Cinema_hall.query.filter(Cinema_hall.ch_cinemaId == cinema.cinemaId, Cinema_hall.hallNum == hallNum).first()
    movie_session = Movie_session.query.filter(Movie_session.ms_hallId == cinema_hall.hallId, Movie_session.date == date, Movie_session.startTime == startTime).first()
    if not movie_session:
        abort(404)
    return jsonify({'sessionId': movie_session.sessionId}), 200


#2获取电影院某电影三天内所有场次电影信息
@app.route('/resource/cinema/<cinemaName>/movie/<movieName>', methods=['GET'])
def get_all_movie_session(cinemaName, movieName):
    cinema = Cinema.query.filter(Cinema.cinemaName == cinemaName).first()
    movie = Movie.query.filter(Movie.movieName == movieName).first()
    time_local = datetime.datetime.now()
    delta = datetime.timedelta(days = 2)
    third_day = time_local+delta
    startDate = time_local.day+time_local.month*100+time_local.year*10000
    endDate = third_day.day+third_day.month*100+third_day.year*10000
    nowTime = time_local.minute+time_local.hour*100
    movie_sessions1 = Movie_session.query.filter(Movie_session.ms_cinemaId == cinema.cinemaId, Movie_session.ms_movieId == movie.movieId, Movie_session.date > startDate, Movie_session.date <= endDate).all()
    movie_sessions2 = Movie_session.query.filter(Movie_session.ms_cinemaId == cinema.cinemaId, Movie_session.ms_movieId == movie.movieId, Movie_session.date == startDate, Movie_session.startTime > nowTime).all()
    if len(movie_sessions1)+len(movie_sessions2) == 0:
        abort(404)
    ms_list = []
    for index in range(len(movie_sessions1)):
        cinema_hall = Cinema_hall.query.filter(Cinema_hall.hallId == movie_sessions1[index].ms_hallId).first()
        temp = {
            'date':movie_sessions1[index].date,
            'startTime':movie_sessions1[index].startTime,
            'hallNum':cinema_hall.hallNum,
            'price':movie_sessions1[index].price
        }
        ms_list.append(temp)

    for index in range(len(movie_sessions2)):
        cinema_hall = Cinema_hall.query.filter(Cinema_hall.hallId == movie_sessions2[index].ms_hallId).first()
        temp = {
            'date':movie_sessions2[index].date,
            'startTime':movie_sessions2[index].startTime,
            'hallNum':cinema_hall.hallNum,
            'price':movie_sessions2[index].price
        }
        ms_list.append(temp)

    return jsonify(ms_list), 200



#座位类

#1获取某场次座位信息
@app.route('/resource/movie_session/<int:sessionId>/<int:seatRow>/<int:seatCol>', methods=['GET'])
def get_seat(sessionId, seatRow, seatCol):
    movie_session = Movie_session.query.filter(Movie_session.sessionId == sessionId).first()
    seat = Seat.query.filter(Seat.s_sessionId == sessionId, Seat.seatRow == seatRow, Seat.seatCol == seatCol).first()
    if not seat:
        abort(404)#未售出
    return jsonify({'isSold': 'isSold'}), 200#已售出


#电影类

#1获取电影信息
@app.route('/resource/movie/<movieName>', methods=['GET'])
def get_movie(movieName):
    movie = Movie.query.filter(Movie.movieName == movieName).first()
    if not movie:
        abort(404)
    return jsonify({'movieName': movie.movieName, 'movieType': movie.movieType, 'director': movie.director, 'starring': movie.starring, 'length': movie.length, 'englishName': movie.englishName, 'showDate': movie.showDate, 'introduction': movie.introduction, 'grade':movie.grade}), 200


#2获取所有电影信息
@app.route('/resource/movie', methods=['GET'])
def get_movie_all():
    movies = Movie.query.all()
    if len(movies) == 0:
        abort(404)
    movie_list = []
    for index in range(len(movies)):
        temp = {
            'movieName': movies[index].movieName,
            'movieType': movies[index].movieType,
            'director': movies[index].director,
            'starring': movies[index].starring,
            'length': movies[index].length,
            'englishName': movies[index].englishName,
            'showDate': movies[index].showDate,
            'introduction': movies[index].introduction,
            'grade':movies[index].grade
        }
        movie_list.append(temp)
    return jsonify(movie_list), 200


#电影票类

#1获取用户所有电影票信息
@app.route('/resource/tickets/<phone>', methods=['GET'])
def get_all_tickets(phone):
    user = User.query.filter(User.phone == phone).first()
    tickets = Ticket.query.filter(Ticket.tk_userId == user.userId).all()
    if len(tickets) == 0:
        return jsonify({'code': 200}), 200
    ticket_list = []
    for index in range(len(tickets)):
        cinema = Cinema.query.filter(Cinema.cinemaId == tickets[index].tk_cinemaId).first()
        cinema_hall = Cinema_hall.query.filter(Cinema_hall.hallId == tickets[index].tk_hallId).first()
        seat = Seat.query.filter(Seat.seatId == tickets[index].tk_seat).first()
        movie = Movie.query.filter(Movie.movieId == tickets[index].tk_movieId).first()
        movie_session = Movie_session.query.filter(Movie_session.sessionId == tickets[index].tk_sessionId).first()
        temp = {
            'ticketId':tickets[index].ticketId,
            'userName':user.userName,
            'movieName':movie.movieName,
            'date':movie_session.date,
            'startTime':movie_session.startTime,
            'cinemaName':cinema.cinemaName,
            'hallNum':cinema_hall.hallNum,
            'price':movie_session.price,
            'seatRow':seat.seatRow,
            'seatCol':seat.seatCol,
            'isPrinted':tickets[index].isPrinted
        }
        ticket_list.append(temp)
    return jsonify(ticket_list), 200


#2用户购买电影票
@app.route('/resource/tickets', methods=['POST'])
def create_ticket():
    if not request.json:
        abort(404)
    cinema = Cinema.query.filter(Cinema.cinemaName == request.json['cinemaName']).first()
    user = User.query.filter(User.phone == request.json['phone']).first()
    movie_session = Movie_session.query.filter(Movie_session.sessionId == request.json['sessionId']).first()
    if Seat.query.filter(Seat.s_sessionId == request.json['sessionId'], Seat.seatRow == request.json['seatRow'], Seat.seatCol == request.json['seatCol']).first():
        abort(408)
    seat = Seat()
    seat.seatRow = request.json['seatRow']
    seat.seatCol = request.json['seatCol']
    seat.s_sessionId = request.json['sessionId']
    db.session.add(seat)
    db.session.commit()
    seat = Seat.query.filter(Seat.s_sessionId == request.json['sessionId'], Seat.seatRow == request.json['seatRow'], Seat.seatCol == request.json['seatCol']).first()
    ticket = Ticket()
    ticket.isPrinted = False
    ticket.tk_userId = user.userId
    ticket.tk_cinemaId = cinema.cinemaId
    ticket.tk_movieId = movie_session.ms_movieId
    ticket.tk_sessionId = request.json['sessionId']
    ticket.tk_hallId = movie_session.ms_hallId
    ticket.tk_seat = seat.seatId
    t_id = random.randint(10000000, 99999999)
    while(Ticket.query.filter(Ticket.ticketId == t_id).first()):
        t_id = random.randint(10000000, 99999999)
    ticket.ticketId = t_id
    db.session.add(ticket)
    db.session.commit()
    return jsonify({'ticketId': ticket.ticketId}), 200


#返回码

#账号已存在
@app.errorhandler(405)
def account_exists(error):
    return make_response(jsonify({'error': 'account exists'}), 405)


#手机号已存在
@app.errorhandler(403)
def phone_exists(error):
    return make_response(jsonify({'error': 'phone exists'}), 403)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


#手机或账号不存在
@app.errorhandler(406)
def phone_exists(error):
    return make_response(jsonify({'error': 'acoount or phone not found'}), 406)


#密码错误
@app.errorhandler(401)
def phone_exists(error):
    return make_response(jsonify({'error': 'wrong password'}), 401)


#座位不为空
@app.errorhandler(408)
def phone_exists(error):
    return make_response(jsonify({'error': 'seat not empty'}), 408)


if __name__ == '__main__':
    app.run(debug=True)

#last change:20180629 12:15
