<<<<<<< HEAD
package com.example.newsfeed.friend.service;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.friend.dto.FriendRequestDto;
import com.example.newsfeed.friend.dto.FriendResponseDto;
import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.entity.FriendShip;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.friend.repository.FriendShipRepository;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendRepository friendRepository;
    private final FriendShipRepository friendShipRepository;
    private final UsersRepository usersRepository;

    @Transactional //친구 요청
    public ResponseDto<FriendResponseDto> request(FriendRequestDto dto, String userId){
        // 요청 보낸 사용자 찾기
        Users fromUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Users toUser = usersRepository.findById(dto.getFriend())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 친구 요청을 받는 사용자 찾기
//        Users toUser = dto.getFriend();  //FriendRequestDto에서 친구 객체를 받아서 바로 사용 id
        //friendid로 받아와서 유저를 확인하고 받아와야함.


//        if (friendRepository.existsByUsersAndFriend(fromUser, toUser)) {
//            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
//        }

        // 기존 친구 요청이 있는지 확인 (중복 요청 방지)
        if(friendRepository.findByFriendIdAndUsersId(dto.getFriend(), userId).isPresent()){
            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
        }

        // 새로운 친구 요청 생성 (상태: 0 = 요청 상태)
        Friend friendRequest = new Friend(fromUser, toUser);
        friendRepository.save(friendRequest);

        // 응답 DTO
        return ResponseDto.success(new FriendResponseDto(friendRequest.getId(), fromUser.getId(), toUser.getId(), friendRequest.getCreatedAt()));
    }

    @Transactional // 친구 요청 수락
    public String acceptFriend(Long friendId, String userId) {
        Users fromUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Friend friendRequest = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("friend_id가 없습니다. _" + friendId));

//        Users toUser = usersRepository.findById(friendRequest.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("요청 받은 사용자가 존재하지 않습니다."));

        // 친구 요청 수락: FriendShip 테이블에 저장
//        FriendShip toUserFriendship = new FriendShip(toUser, fromUser, (short) 1);
        //FriendShip fromUserFriendship = new FriendShip(fromUser, toUser, (short) 1);

=======
//package com.example.newsfeed.friend.service;
//
//import com.example.newsfeed.friend.dto.FriendRequestDto;
//import com.example.newsfeed.friend.dto.FriendResponseDto;
//import com.example.newsfeed.friend.entity.Friend;
//import com.example.newsfeed.friend.entity.FriendShip;
//import com.example.newsfeed.friend.repository.FriendRepository;
//import com.example.newsfeed.friend.repository.FriendShipRepository;
//import com.example.newsfeed.users.entity.Users;
//import com.example.newsfeed.users.repository.UsersRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Getter
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class FriendShipService {
//    private final FriendRepository friendRepository;
//    private final FriendShipRepository friendShipRepository;
//    private final UsersRepository usersRepository;
//
//    @Transactional //친구 요청
//    public FriendResponseDto request(FriendRequestDto dto){
//        // 요청 보낸 사용자 찾기
//        Users fromUser = usersRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getUserId()));
//
//        // 친구 요청을 받는 사용자 찾기
//        Users toUser = dto.getFriend().getId();  //FriendRequestDto에서 친구 객체를 받아서 바로 사용
//        //friendid로 받아와서 유저를 확인하고 받아와야함.
//
//        // 기존 친구 요청이 있는지 확인 (중복 요청 방지)
//        if (friendRepository.existsByUsersAndFriend(fromUser, toUser)) {
//            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
//        }
//
//        // 새로운 친구 요청 생성 (상태: 0 = 요청 상태)
//        Friend friendRequest = new Friend(fromUser, toUser, (byte) 0);
//        friendRepository.save(friendRequest);
//
//        // 응답 DTO
//        return new FriendResponseDto(friendRequest.getId(), fromUser.getId(), toUser.getId(), friendRequest.getStatus(), friendRequest.getCreatedAt());
//    }
//
//    @Transactional // 친구 요청 수락
//    public String acceptFriend(HttpServletRequest request, Long friend_id) {
//
//        Friend friendRequest = friendRepository.findById(friend_id)
//                .orElseThrow(() -> new IllegalArgumentException("friend_id가 없습니다. _" + friend_id));
//
//        Users fromUser = usersRepository.findById(friendRequest.getUsers().getId())
//                .orElseThrow(() -> new IllegalArgumentException("요청 보낸 사용자가 존재하지 않습니다."));
//
//        Users toUser = usersRepository.findById(friendRequest.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("요청 받은 사용자가 존재하지 않습니다."));
//
//        // 친구 요청 수락: FriendShip 테이블에 저장
//        FriendShip toUserFriendship = new FriendShip(toUser, fromUser, (short) 1);
//        FriendShip fromUserFriendship = new FriendShip(fromUser, toUser, (short) 1);
//
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
//        friendShipRepository.save(toUserFriendship);
//        friendShipRepository.save(fromUserFriendship);
//
//        friendRepository.delete(friendRequest);
<<<<<<< HEAD

        FriendShip updateStatus = new FriendShip((short)1);
        return "친구 추가 완료";
    }

    @Transactional //친구 요청 거절
    public String rejectFriend(Long friendId, String userId){//컨트롤러
        Users fromUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Friend friendRequest = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("요청받은 값이 없습니다. " + friendId));

//        Users toUser = usersRepository.findById(dto.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Friend not found with id: " + dto.getFriend().getId()));

        // 친구 요청을 거절하는 새로운 FriendShip 객체 생성 (status: 2 = 거절 상태)
        FriendShip updateStatus = new FriendShip((short) 2);

        return "친구 거절";//friendShip.getStatus()
    }

    public List<FriendResponseDto> findAll(String userId){//다건 조회 //조회조건 id로
        return friendShipRepository.findAllByUserIdAndStatus(userId, (short)1)
                .stream().map(FriendResponseDto::toDto).toList();

=======
//
//        return "친구 추가 완료";
//    }
//
//    @Transactional //친구 요청 거절
//    public FriendResponseDto rejectFriend(FriendRequestDto dto){//컨트롤러
//        // 요청한 사용자 찾기
//        Users fromUser = usersRepository.findById(dto.getUsersId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getUserId()));
//
//        // 거절하려는 친구 찾기
//        Users toUser = usersRepository.findById(dto.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Friend not found with id: " + dto.getFriend().getId()));
//
//        // 친구 요청을 거절하는 새로운 FriendShip 객체 생성 (status: 2 = 거절 상태)
//        FriendShip toUserFriendship = new FriendShip(toUser, fromUser, (short) 2);
//        FriendShip fromUserFriendship = new FriendShip(fromUser, toUser, (short) 2);
//
//        // FriendShip 객체를 저장 (거절 상태로 친구 요청을 업데이트)
//        friendShipRepository.save(toUserFriendship);
//        friendShipRepository.save(fromUserFriendship);
//
//        return new FriendResponseDto(toUserFriendship.getId(), toUserFriendship.getUser().getId(), toUserFriendship.getFriend().getId(), toUserFriendship.getStatus(), toUserFriendship.getCreatedAt());//friendShip.getStatus()
//    }
//
//    public List<FriendResponseDto> findAll(){//다건 조회 //조회조건 id로
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
//        return friendRepository.findAll()
//                .stream()
//                .map(FriendResponseDto::toDto)
//                .toList();
<<<<<<< HEAD
    }

    public FriendResponseDto findOne(Long id){//단건 조회
        Friend findFriend = friendRepository.findByIdOrElseThrow(id); // 1. id 대조해서 그 id에 맞는 데이터를 조회함.
        return new FriendResponseDto(findFriend.getId(), findFriend.getUsers().getId(), findFriend.getFriend().getId(), findFriend.getCreatedAt());
    }

//6가지
//성공 실패 여부
}
=======
//    }
//
//    public FriendResponseDto findOne(Long id){//단건 조회
//        Friend findFriend = friendRepository.findByIdOrElseThrow(id); // 1. id 대조해서 그 id에 맞는 데이터를 조회함.
//        return new FriendResponseDto(findFriend.getId(), findFriend.getUsers().getId(), findFriend.getFriend().getId(), findFriend.getStatus(), findFriend.getCreatedAt());
//
//    }
//
////6가지
////성공 실패 여부
//}
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
