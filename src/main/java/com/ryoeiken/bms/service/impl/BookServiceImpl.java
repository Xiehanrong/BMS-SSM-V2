package com.ryoeiken.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryoeiken.bms.mapper.BookInfoMapper;
import com.ryoeiken.bms.pojo.BookInfo;
import com.ryoeiken.bms.pojo.BookInfoExample;
import com.ryoeiken.bms.pojo.PageResult;
import com.ryoeiken.bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookInfoMapper bookInfoMapper;

    @Override
    public PageResult<BookInfo> getAllBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookInfo> books = this.bookInfoMapper.selectByExample(null);
        PageInfo<BookInfo> pageInfo = new PageInfo<>(books);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        PageResult<BookInfo> page = new PageResult<>();
        page.setList(books);
        page.setPageNum(pageNum);
        page.setPages(pages);
        page.setPageSize(pageSize);
        page.setTotal(total);

        return page;
    }

    @Override
    public boolean matchBook(String searchWord) {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andNameLike("%" + searchWord + "%");
        return this.bookInfoMapper.countByExample(bookInfoExample) > 0;
    }

    @Override
    public List<BookInfo> queryBook(String searchWord) {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andNameLike("%" + searchWord + "%");
        List<BookInfo> books = this.bookInfoMapper.selectByExample(bookInfoExample);
        return books;
    }

    @Override
    public boolean addBook(BookInfo bookInfo) {
        return this.bookInfoMapper.insertSelective(bookInfo) > 0;
    }

    @Override
    public BookInfo getBook(Long bookId) {
        BookInfo bookInfo = this.bookInfoMapper.selectByPrimaryKey(bookId);
        return bookInfo;
    }

    @Override
    public boolean editBook(BookInfo bookInfo) {
        return this.bookInfoMapper.updateByPrimaryKeySelective(bookInfo) > 0;
    }

    @Override
    public int deleteBook(Long bookId) {
        return this.bookInfoMapper.deleteByPrimaryKey(bookId);
    }

    @Override
    public List<BookInfo> bookRank() {
        BookInfoExample bookInfoExample = new BookInfoExample();
        bookInfoExample.setOrderByClause("count DESC LIMIT 5");

        return this.bookInfoMapper.selectByExample(bookInfoExample);
    }

}
