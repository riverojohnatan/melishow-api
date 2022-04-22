package com.meli.api.service;

import com.google.common.collect.Lists;
import com.meli.api.model.Booking;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.dto.BookingDTO;
import com.meli.api.model.dto.BookingSeatDTO;
import com.meli.api.model.dto.FilterDTO;
import com.meli.api.model.exception.MeliShowException;
import com.meli.api.repository.BookingRepository;
import com.meli.api.repository.SeatRepository;
import com.meli.api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShowService {

    public static final String INVALID_SEAT_ERROR_MESSAGE = "Not valid Seat for Show Id: ";
    public static final String INVALID_SHOW_ID_ERROR_MESSAGE = "Invalid show id: ";

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Show> getShows(FilterDTO filter){
        if (filter.getStartDate() != null && filter.getEndDate() != null
            && filter.getBottomPrice() != null && filter.getTopPrice() != null) {
            List<Long> showIdList = this.seatRepository
                    .getDistinctShowIdByShowDateBetweenAndSeatPriceBetween(filter.getStartDate(), filter.getEndDate(),
                            filter.getBottomPrice(), filter.getTopPrice());
            return this.showRepository.getAllByIdList(showIdList);
        } else if (filter.getStartDate() != null && filter.getEndDate() != null ) {
            List<Long> showIdList = this.seatRepository
                    .getDistinctShowIdByShowDateBetween(filter.getStartDate(), filter.getEndDate());
            return this.showRepository.getAllByIdList(showIdList);
        } else if (filter.getBottomPrice() != null && filter.getTopPrice() != null) {
            List<Long> showIdList = this.seatRepository
                    .getDistinctShowIdBySeatPriceBetween(filter.getBottomPrice(), filter.getTopPrice());
            return this.showRepository.getAllByIdList(showIdList);
        } else {
            return Lists.newArrayList(this.showRepository.findAll());
        }
    }

    public List<Seat> getSeats(Long showId) {
        return this.seatRepository.findByShowId(showId);
    }

    @Transactional
    public void doBooking(BookingDTO bookingDTO) {
        if (bookingDTO.getSeats().size() > 0) {
            Long bookingID = null;
            for(BookingSeatDTO seatDTO : bookingDTO.getSeats()) {
                Seat seat = this.seatRepository.findSeatByShowIdAndRow(seatDTO.getShowId(), seatDTO.getRow());
                validations(seat, seatDTO);

                int seatCount = 0;
                Booking booking = new Booking();
                booking.setId(bookingID);
                booking.setDocument(bookingDTO.getDocument());
                booking.setName(bookingDTO.getName());
                booking.setSeatRow(seatDTO.getRow());
                booking.setShowId(seatDTO.getShowId());
                booking.setSeatPrice(seat.getSeatPrice());

                List<String> seatNumbers = seat.getSeatNumberList();
                List<String> bookingSeatNumbers = seatDTO.getNumbers();

                for (String seatNumber : bookingSeatNumbers) {
                    if (!seatNumbers.contains(seatNumber)) throw new MeliShowException(INVALID_SEAT_ERROR_MESSAGE + seatDTO.getShowId());
                    seatNumbers.remove(seatNumber);
                    seatCount++;
                }

                seat.setSeatNumbers(seatNumbers);

                booking.setSeatNumbers(seatDTO.getSeatNumbers());

                this.seatRepository.save(seat);
                booking = this.bookingRepository.save(booking);
                bookingID = booking.getId();
            }
        }
    }

    private void validations(Seat seat, BookingSeatDTO seatDTO) {
        if (seat == null)  throw new MeliShowException(INVALID_SHOW_ID_ERROR_MESSAGE + seatDTO.getShowId());
        if (seatDTO.getNumbers().size() > seat.getSeatNumberList().size()) throw new MeliShowException(INVALID_SEAT_ERROR_MESSAGE + seatDTO.getShowId());
    }
}
